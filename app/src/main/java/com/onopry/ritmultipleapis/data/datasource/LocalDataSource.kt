package com.onopry.ritmultipleapis.data.datasource

import android.content.Context
import com.onopry.ritmultipleapis.R
import com.onopry.ritmultipleapis.presentation.select.ApiSelected

class LocalDataSource(private val context: Context) {
    private val dogApi = 1
    private val nationalizeApi = 2
    private val ownApi = 3
    private val prefsApiKey = "Selected"


    fun saveSelectedApi(api: ApiSelected): Boolean {
        val prefs = context.getSharedPreferences(
            context.getString(R.string.shared_prefs_key),
            Context.MODE_PRIVATE
        ) ?: return false
        prefs.edit().apply {
            when (api) {
                ApiSelected.Dogs -> putInt(prefsApiKey, dogApi).apply()
                ApiSelected.Nationalize -> putInt(prefsApiKey, nationalizeApi).apply()
                ApiSelected.Own -> putInt(prefsApiKey, ownApi).apply()
            }
        }
        return true
    }

    fun getSelectedApi(): ApiSelected? {
        val prefs = context.getSharedPreferences(
            context.getString(R.string.shared_prefs_key),
            Context.MODE_PRIVATE
        )

        return when (prefs.getInt(prefsApiKey, 0)) {
            dogApi -> ApiSelected.Dogs
            nationalizeApi -> ApiSelected.Nationalize
            ownApi -> ApiSelected.Own
            else -> return null
        }

    }
}