package com.onopry.ritmultipleapis.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.onopry.ritmultipleapis.R
import com.onopry.ritmultipleapis.databinding.FragmentDogImageBinding
import com.onopry.ritmultipleapis.presentation.ViewModelFactory
import com.onopry.ritmultipleapis.presentation.utils.ScreenState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Thread.State


class DogImageFragment : Fragment(R.layout.fragment_dog_image) {
    private lateinit var binding: FragmentDogImageBinding
    private val imageUrlKey = "DOG-KEY"

    private val viewModel: MainViewModel by activityViewModels { ViewModelFactory(requireActivity()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDogImageBinding.bind(view)

//        val state = viewModel.screenState.value
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenState.collectLatest { state ->
                    if (state is ScreenState.DogApi.Data)
                        Glide.with(requireContext())
                            .load(state.url)
//                            .load(arguments?.getString(imageUrlKey))
                            .into(binding.image)
                }
            }
        }

        binding.image.setOnClickListener {
            parentFragmentManager.popBackStack()
            parentFragmentManager.commit {
                remove(this@DogImageFragment)
            }
            println("Close")
        }
    }
}