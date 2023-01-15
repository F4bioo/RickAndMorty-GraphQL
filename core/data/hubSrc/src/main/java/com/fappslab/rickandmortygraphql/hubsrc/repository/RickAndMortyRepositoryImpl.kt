package com.fappslab.rickandmortygraphql.hubsrc.repository

import com.fappslab.rickandmortygraphql.domain.model.Characters
import com.fappslab.rickandmortygraphql.domain.model.Filter
import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import com.fappslab.rickandmortygraphql.hubsrc.extension.toCharacters
import com.fappslab.rickandmortygraphql.hubsrc.extension.toFilterCharacter
import com.fappslab.rickandmortygraphql.remote.source.RickAndMortyDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RickAndMortyRepositoryImpl(
    private val remoteDataSource: RickAndMortyDataSource
) : RickAndMortyRepository {

    override fun getCharactersFilter(filter: Filter): Flow<Characters> {
        return remoteDataSource.getCharactersFilter(filter.page, filter.toFilterCharacter())
            .map { it.characters.toCharacters() }
    }
}
