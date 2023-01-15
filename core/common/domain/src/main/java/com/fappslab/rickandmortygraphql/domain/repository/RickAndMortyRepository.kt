package com.fappslab.rickandmortygraphql.domain.repository

import com.fappslab.rickandmortygraphql.domain.model.Characters
import com.fappslab.rickandmortygraphql.domain.model.Filter
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun getCharactersFilter(filter: Filter): Flow<Characters>
}
