package com.fappslab.rickandmortygraphql.domain.repository

import com.fappslab.rickandmortygraphql.domain.model.Characters
import com.fappslab.rickandmortygraphql.domain.model.Filter
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun getFilter(prefsKey: String): Flow<String?>
    fun setFilter(prefsKey: String, value: String): Flow<Unit>
    fun deleteFilter(prefsKey: String): Flow<Unit>
    fun getCharactersFilter(filter: Filter): Flow<Characters>
}
