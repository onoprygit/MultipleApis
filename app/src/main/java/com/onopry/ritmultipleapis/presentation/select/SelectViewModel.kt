package com.onopry.ritmultipleapis.presentation.select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onopry.ritmultipleapis.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ApiSelected {
    object Dogs : ApiSelected()
    object Nationalize : ApiSelected()
    object Own : ApiSelected()
}

class SelectViewModel(private val repository: Repository) : ViewModel() {

    private val mutableSelectedApi = MutableStateFlow<ApiSelected?>(null)
    val selectedApi: StateFlow<ApiSelected?> = mutableSelectedApi.asStateFlow()

    fun checkSavedApi() {
        viewModelScope.launch {
            mutableSelectedApi.emit(repository.getSelectedApi())
        }
    }

    fun changeApiTo(api: ApiSelected) {
        if (mutableSelectedApi.value == api) return
        viewModelScope.launch { mutableSelectedApi.emit(api) }
        repository.saveSelectedApi(api)
    }

}