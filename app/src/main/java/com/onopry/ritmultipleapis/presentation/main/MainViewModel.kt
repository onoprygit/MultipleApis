package com.onopry.ritmultipleapis.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onopry.ritmultipleapis.data.repository.Repository
import com.onopry.ritmultipleapis.data.model.DogResponse
import com.onopry.ritmultipleapis.data.model.NationalizeItem
import com.onopry.ritmultipleapis.presentation.utils.ScreenState
import com.onopry.ritmultipleapis.presentation.select.ApiSelected
import com.onopry.ritmultipleapis.utils.ApiResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.text.StringBuilder

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val mutableSelectedStateFlow = MutableStateFlow<ApiSelected?>(null)
    val apiSelectStateFlow = mutableSelectedStateFlow.asStateFlow()

    private val dogResponse = MutableStateFlow<ApiResult<DogResponse>>(ApiResult.Empty())
    private val nationalizeResponse =
        MutableStateFlow<ApiResult<List<NationalizeItem>>>(ApiResult.Empty())
    private val ownApiResponse = MutableStateFlow<ApiResult<String>>(ApiResult.Empty())

    private val screenStateMutableFlow = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val screenState = screenStateMutableFlow.asStateFlow()

    fun checkSavedApi() {
        viewModelScope.launch {
            val selected = repository.getSelectedApi()
            if (mutableSelectedStateFlow.value == selected) return@launch
            mutableSelectedStateFlow.emit(selected)
        }
    }

    fun requestDog() {
        viewModelScope.launch {
            val res = repository.getDog()
            dogResponse.emit(res)
        }
    }

    fun requestNationalize(input: String) {
        if (input.isBlank() || input.length < 2) {
            viewModelScope.launch { ScreenState.Error(msg = "Empty names input") }
            return
        }

        val queriesArray = checkNationalizeInput(input)
        viewModelScope.launch {
            val res = repository.getNationalize(*queriesArray)
            nationalizeResponse.emit(res)
        }
        input.split(",")
    }

    private fun checkNationalizeInput(input: String) =
        input.split(", ", ignoreCase = true).toTypedArray()

    fun requestOwn(apiUrl: String) {
        if (apiUrl.isNotBlank() && apiUrl.contains("http") && apiUrl.contains("://")) {
            viewModelScope.launch {
                val res = repository.getOwnApi(apiUrl)
                ownApiResponse.emit(res)
            }
        } else {
            viewModelScope.launch {
                screenStateMutableFlow.emit(ScreenState.Error("Bad custom API input"))
            }
        }
    }

    private fun mapNationalizeToString(items: List<NationalizeItem>): String =
        StringBuilder()
            .apply {
                items.forEach { nationalizeItem ->
                    append("\t\t${nationalizeItem.name}:\n")
                    nationalizeItem.country.forEach { country ->
                        append("\tCountry: ${country.countryId} \t|\t Probability: ${country.probability}\n")
                    }
                }
            }.toString()

    /*fun getSelectedApi(): ApiSelected {
        viewModelScope.launch {

        }
    }*/

    init {
        dogResponse.onEach { response ->
            when (response) {
                is ApiResult.Empty -> screenStateMutableFlow.emit(ScreenState.Empty)
                is ApiResult.Error -> screenStateMutableFlow.emit(
                    ScreenState.Error(
                        msg = "HTTP error with code ${response.code}: \n ${response.message ?: "Unexpected Error Occurred"}"
                    )
                )
                is ApiResult.Exception -> screenStateMutableFlow.emit(
                    ScreenState.Error(
                        msg = "Unexpected exception: ${response.exc.message}"
                    )
                )
                is ApiResult.Success -> screenStateMutableFlow.emit(ScreenState.DogApi.Data(url = response.data.imageUrl))
            }
        }.launchIn(viewModelScope)

        nationalizeResponse.onEach { response ->
            when (response) {
                is ApiResult.Empty -> screenStateMutableFlow.emit(ScreenState.Empty)

                is ApiResult.Error -> ScreenState.Error(
                    msg = "HTTP error with code " +
                            "${response.code}: \n ${response.message ?: "Unexpected Error Occurred"}"
                )

                is ApiResult.Exception -> screenStateMutableFlow.emit(
                    ScreenState.Error(msg = "Unexpected exception: ${response.exc.message}")
                )

                is ApiResult.Success -> screenStateMutableFlow.emit(
                    ScreenState.NationalizeApi.Data(
                        data = mapNationalizeToString(response.data)
                    )
                )
            }
        }.launchIn(viewModelScope)

        ownApiResponse.onEach { response ->
            when (response) {
                is ApiResult.Empty -> screenStateMutableFlow.emit(ScreenState.Empty)

                is ApiResult.Error -> ScreenState.Error(
                    msg = "HTTP error with code " +
                            "${response.code}: \n ${response.message ?: "Unexpected Error Occurred"}"
                )

                is ApiResult.Exception -> screenStateMutableFlow.emit(
                    ScreenState.Error(msg = "Unexpected exception: ${response.exc.message}")
                )

                is ApiResult.Success -> screenStateMutableFlow.emit(
                    ScreenState.OwnApi.Data(
                        data = response.data
                    )
                )
            }
        }.launchIn(viewModelScope)
    }
}