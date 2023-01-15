package com.fappslab.rickandmortygraphql.filter.data.local

internal interface DataPrefs {
    suspend fun getFilter(prefsKey: String): String?
    suspend fun setFilter(prefsKey: String, value: String)
    suspend fun deleteFilter(prefsKey: String)
}
