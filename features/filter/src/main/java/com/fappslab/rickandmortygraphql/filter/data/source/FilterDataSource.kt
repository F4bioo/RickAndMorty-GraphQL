package com.fappslab.rickandmortygraphql.filter.data.source

import kotlinx.coroutines.flow.Flow

internal interface FilterDataSource {
    fun getFilter(prefsKey: String): Flow<String?>
    fun setFilter(prefsKey: String, value: String): Flow<Unit>
    fun deleteFilter(prefsKey: String): Flow<Unit>
}
