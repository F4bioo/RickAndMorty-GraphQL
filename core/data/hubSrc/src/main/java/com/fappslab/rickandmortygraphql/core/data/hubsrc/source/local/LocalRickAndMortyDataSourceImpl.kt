package com.fappslab.rickandmortygraphql.core.data.hubsrc.source.local

import android.content.SharedPreferences
import com.fappslab.rickandmortygraphql.core.data.local.source.LocalRickAndMortyDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalRickAndMortyDataSourceImpl(
    private val prefs: SharedPreferences
) : LocalRickAndMortyDataSource {

    override fun getFilter(prefsKey: String): Flow<String?> = flow {
        emit(prefs.getString(prefsKey, null))
    }

    override fun setFilter(prefsKey: String, value: String): Flow<Unit> = flow {
        emit(prefs.edit().putString(prefsKey, value).apply())
    }

    override fun deleteFilter(prefsKey: String): Flow<Unit> = flow {
        emit(prefs.edit().remove(prefsKey).apply())
    }
}
