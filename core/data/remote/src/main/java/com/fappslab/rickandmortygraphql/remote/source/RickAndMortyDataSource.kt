package com.fappslab.rickandmortygraphql.remote.source

import com.fappslab.rickandmortygraphql.remote.GetCharactersFilterQuery
import com.fappslab.rickandmortygraphql.remote.type.FilterCharacter
import kotlinx.coroutines.flow.Flow

interface RickAndMortyDataSource {
    fun getCharactersFilter(page: Int, filter: FilterCharacter): Flow<GetCharactersFilterQuery.Data>
}
