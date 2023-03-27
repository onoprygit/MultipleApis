package com.onopry.ritmultipleapis.presentation.select

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.onopry.ritmultipleapis.R
import com.onopry.ritmultipleapis.databinding.ActivitySelectBinding
import com.onopry.ritmultipleapis.presentation.ViewModelFactory
import com.onopry.ritmultipleapis.utils.observeWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SelectActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectBinding
    private val viewModel: SelectViewModel by viewModels { ViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.checkSavedApi()

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbDogsApi -> viewModel.changeApiTo(ApiSelected.Dogs)
                R.id.rbNationalizeApi -> viewModel.changeApiTo(ApiSelected.Nationalize)
                R.id.rbOwnApi -> viewModel.changeApiTo(ApiSelected.Own)
            }
        }

        observeWithLifecycle(Lifecycle.State.STARTED) {
            viewModel.selectedApi.collectLatest { api ->
                when (api) {
                    ApiSelected.Dogs -> binding.radioGroup.check(R.id.rbDogsApi)
                    ApiSelected.Nationalize -> binding.radioGroup.check(R.id.rbNationalizeApi)
                    ApiSelected.Own -> binding.radioGroup.check(R.id.rbOwnApi)
                    else -> {}
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}