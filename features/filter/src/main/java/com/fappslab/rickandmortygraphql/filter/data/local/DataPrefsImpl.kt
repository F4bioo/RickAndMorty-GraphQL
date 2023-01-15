package com.fappslab.rickandmortygraphql.filter.data.local

import android.content.Context

private const val DEFAULT_NAME = "DataPrefs"

internal class DataPrefsImpl(
    private val context: Context,
    private val prefsName: String = DEFAULT_NAME
) : DataPrefs {

    private val prefs by lazy {
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    }

    override suspend fun getFilter(prefsKey: String): String? =
        prefs.getString(prefsKey, null)

    override suspend fun setFilter(prefsKey: String, value: String) =
        prefs.edit().putString(prefsKey, value).apply()

    override suspend fun deleteFilter(prefsKey: String) =
        prefs.edit().remove(prefsKey).apply()
}
