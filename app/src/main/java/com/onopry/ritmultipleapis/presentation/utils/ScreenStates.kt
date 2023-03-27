package com.onopry.ritmultipleapis.presentation.utils

sealed class ScreenState {
    object Empty : ScreenState()
    class Error(val msg: String) : NationalizeApi()

    sealed class DogApi : ScreenState() {
        object Empty : DogApi()
        class Data(val url: String) : DogApi()
    }

    sealed class NationalizeApi : ScreenState() {
        object Empty : NationalizeApi()
        class Data(val data: String) : NationalizeApi()
    }

    sealed class OwnApi : ScreenState() {
        object Empty : OwnApi()
        class Data(val data: String) : OwnApi()
    }
}