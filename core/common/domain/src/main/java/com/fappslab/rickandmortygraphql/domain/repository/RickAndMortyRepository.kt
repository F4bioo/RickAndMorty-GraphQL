package com.fappslab.rickandmortygraphql.domain.repository

import com.fappslab.rickandmortygraphql.domain.model.Characters
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun getCharacters(page: Int): Flow<Characters>
}
