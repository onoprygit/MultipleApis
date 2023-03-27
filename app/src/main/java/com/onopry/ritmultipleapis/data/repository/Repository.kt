package com.onopry.ritmultipleapis.data.repository

import android.content.Context
import com.onopry.ritmultipleapis.data.datasource.DogDataSource
import com.onopry.ritmultipleapis.data.datasource.LocalDataSource
import com.onopry.ritmultipleapis.data.datasource.NationalizeDataSource
import com.onopry.ritmultipleapis.data.datasource.OwnDataSource
import com.onopry.ritmultipleapis.presentation.select.ApiSelected
import com.squareup.moshi.Moshi

class Repository(context: Context) {
    private val moshi: Moshi by lazy { Moshi.Builder().build() }

    private val dogSource: DogDataSource by lazy { DogDataSource(moshi) }
    private val nationalizeSource: NationalizeDataSource by lazy { NationalizeDataSource(moshi) }
    private val ownDataSource: OwnDataSource by lazy { OwnDataSource() }
    private val localSource: LocalDataSource by lazy { LocalDataSource(context) }

    suspend fun getNationalize(vararg names: String) =
        nationalizeSource.getNationalize(*names)

    suspend fun getDog() =
        dogSource.getSingleDog()

    suspend fun getOwnApi(url: String) = ownDataSource.fetchOwnApi(url)

    fun saveSelectedApi(api: ApiSelected) =
        localSource.saveSelectedApi(api)

    fun getSelectedApi() =
        localSource.getSelectedApi()



}