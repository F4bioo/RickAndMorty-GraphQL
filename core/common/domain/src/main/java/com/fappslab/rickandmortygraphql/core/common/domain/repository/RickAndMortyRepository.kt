package com.fappslab.rickandmortygraphql.core.common.domain.repository

import com.fappslab.rickandmortygraphql.core.common.domain.model.Characters
import com.fappslab.rickandmortygraphql.core.common.domain.model.Filter
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun getFilter(prefsKey: String): Flow<String?>
    fun setFilter(prefsKey: String, value: String): Flow<Unit>
    fun deleteFilter(prefsKey: String): Flow<Unit>
    fun getCharactersFilter(filter: Filter): Flow<Characters>
}
