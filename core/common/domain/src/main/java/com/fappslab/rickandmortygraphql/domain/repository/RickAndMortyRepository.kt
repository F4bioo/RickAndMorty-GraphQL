package com.fappslab.rickandmortygraphql.domain.repository

import com.fappslab.rickandmortygraphql.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun getCharacters(page: Int): Flow<List<Character>>
}
