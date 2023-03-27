package com.onopry.ritmultipleapis.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onopry.ritmultipleapis.data.repository.Repository
import com.onopry.ritmultipleapis.presentation.main.MainViewModel
import com.onopry.ritmultipleapis.presentation.select.SelectViewModel

class ViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val repository: Repository by lazy { Repository(context) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        SelectViewModel::class.java -> SelectViewModel(repository)
        MainViewModel::class.java -> MainViewModel(repository)
        else -> throw IllegalStateException("Unknown ViewModel class")
    } as T
}
