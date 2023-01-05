package com.fappslab.rickandmortygraphql.remote.source

import com.fappslab.rickandmortygraphql.remote.GetCharactersQuery
import kotlinx.coroutines.flow.Flow

interface RickAndMortyDataSource {
    fun getCharacters(page: Int): Flow<GetCharactersQuery.Characters?>
}
