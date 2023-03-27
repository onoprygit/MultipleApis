package com.onopry.ritmultipleapis.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.onopry.ritmultipleapis.presentation.main.MainActivity
import com.onopry.ritmultipleapis.presentation.select.ApiSelected
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun MainActivity.observeWithLifecycle(state: Lifecycle.State, block: suspend () -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            block()
        }
    }
}