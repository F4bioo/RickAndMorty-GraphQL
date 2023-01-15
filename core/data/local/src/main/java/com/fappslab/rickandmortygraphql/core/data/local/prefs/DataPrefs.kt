package com.fappslab.rickandmortygraphql.core.data.local.prefs

import android.content.Context
import android.content.SharedPreferences

class DataPrefs(
    private val context: Context,
    private val prefsName: String
) {

    private val prefs by lazy {
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    }

    fun create(): SharedPreferences = prefs
}
