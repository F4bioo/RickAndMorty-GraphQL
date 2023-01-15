package com.fappslab.rickandmortygraphql.core.data.local.source

import kotlinx.coroutines.flow.Flow

interface LocalRickAndMortyDataSource {
    fun getFilter(prefsKey: String): Flow<String?>
    fun setFilter(prefsKey: String, value: String): Flow<Unit>
    fun deleteFilter(prefsKey: String): Flow<Unit>
}
