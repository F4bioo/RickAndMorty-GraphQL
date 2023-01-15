package com.fappslab.rickandmortygraphql.filter.data.source

import com.fappslab.rickandmortygraphql.filter.data.local.DataPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class FilterDataSourceImpl(
    private val prefs: DataPrefs
) : FilterDataSource {

    override fun getFilter(prefsKey: String): Flow<String?> = flow {
        emit(prefs.getFilter(prefsKey))
    }

    override fun setFilter(prefsKey: String, value: String): Flow<Unit> = flow {
        emit(prefs.setFilter(prefsKey, value))
    }

    override fun deleteFilter(prefsKey: String): Flow<Unit> = flow {
        emit(prefs.deleteFilter(prefsKey))
    }
}
