package com.onopry.ritmultipleapis.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.onopry.ritmultipleapis.R
import com.onopry.ritmultipleapis.databinding.ActivityMainBinding
import com.onopry.ritmultipleapis.presentation.utils.ScreenState
import com.onopry.ritmultipleapis.presentation.ViewModelFactory
import com.onopry.ritmultipleapis.presentation.select.ApiSelected
import com.onopry.ritmultipleapis.presentation.select.SelectActivity
import com.onopry.ritmultipleapis.utils.gone
import com.onopry.ritmultipleapis.utils.hide
import com.onopry.ritmultipleapis.utils.observeWithLifecycle
import com.onopry.ritmultipleapis.utils.show
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels { ViewModelFactory(this) }
    private val dialog: NationalizeDialogFragment by lazy { NationalizeDialogFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleScreenState()
        handleApi()
//        .show(supportFragmentManager, null)
//        handleApi()
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkSavedApi()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> {
            startActivity(Intent(this, SelectActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun handleApi() {
        observeWithLifecycle(Lifecycle.State.STARTED) {
            viewModel.apiSelectStateFlow.collectLatest {
                when (it) {
                    ApiSelected.Dogs -> dogsApiSelected()
                    ApiSelected.Nationalize -> nationalizeSelected()
                    ApiSelected.Own -> ownApiSelected()
                    null -> binding.apiName.text = "Select API"
                }
            }
        }
    }

    private fun handleScreenState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenState.collectLatest { state ->
                    with(binding) {
                        when (state) {
                            is ScreenState.DogApi.Data -> {
                                dogApiResultImage.show()
                                errorMessage.gone()
                                loadDogImage(state.url)
                            }
                            is ScreenState.DogApi.Empty -> {
                                dogApiResultImage.hide()
                            }

                            is ScreenState.NationalizeApi.Data -> {
                                dogApiResultImage.gone()
                                errorMessage.gone()
                                ownApiResultText.gone()

                                dialog.data = state.data
                                dialog.show(supportFragmentManager, null)
//                                nationalizeResultText.text = state.data
                            }
                            is ScreenState.NationalizeApi.Empty -> {
                                errorMessage.gone()
                                ownApiResultText.gone()
                                dogApiResultImage.gone()
                            }

                            is ScreenState.OwnApi.Data -> {
                                errorMessage.gone()
                                dogApiResultImage.gone()

                                ownApiResultText.show()
                                ownApiResultText.text = state.data
                            }
                            ScreenState.OwnApi.Empty -> {
                                errorMessage.gone()
                                ownApiResultText.hide()
                                dogApiResultImage.gone()
                            }

                            is ScreenState.Empty -> {
                                dogApiResultImage.gone()
                                ownApiResultText.gone()
                                errorMessage.gone()
                            }
                            is ScreenState.Error -> {
                                dogApiResultImage.gone()
                                ownApiResultText.gone()

                                errorMessage.show()
                                errorMessage.text = state.msg
                            }
                        }
                    }
                }
            }
        }
    }

    private fun dogsApiSelected() {
//        val dogFragment =
            /*.also {
                Bundle().putString("DOG-KEY")
            }*/
        with(binding) {
            apiName.text = getString(R.string.radio_api_from_dog_ceo)
            usersInputText.gone()
            dogApiResultImage.show()

            submitButton.setOnClickListener(null)
            submitButton.setOnClickListener {
                viewModel.requestDog()
            }
            dogApiResultImage.setOnClickListener {
                supportFragmentManager.commit {
                    replace(R.id.fragmentContainer, DogImageFragment())
                    addToBackStack(null)
//                    show(dogFragment)
                }
            }

        }
    }

    private fun loadDogImage(url: String) {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.dogApiResultImage)
    }

    private fun nationalizeSelected() {
        with(binding) {
            apiName.text = getString(R.string.radio_api_from_nationalize_io)

            dogApiResultImage.gone()
            usersInputText.show()
            binding.usersInputText.clearFocus()
            binding.usersInputText.setText("")

            submitButton.setOnClickListener(null)
            submitButton.setOnClickListener {
                viewModel.requestNationalize(binding.usersInputText.text.toString())
            }
        }
    }

    private fun ownApiSelected() {
        with(binding) {
            apiName.text = getString(R.string.radio_your_api)

            dogApiResultImage.gone()
            usersInputText.show()
            binding.usersInputText.clearFocus()
            binding.usersInputText.setText("")

            submitButton.setOnClickListener(null)
            submitButton.setOnClickListener {
                viewModel.requestOwn(binding.usersInputText.text.toString())
            }

        }
    }
}