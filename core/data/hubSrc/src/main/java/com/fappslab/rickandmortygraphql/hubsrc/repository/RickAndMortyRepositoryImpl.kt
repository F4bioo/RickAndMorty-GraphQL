package com.fappslab.rickandmortygraphql.hubsrc.repository

import com.fappslab.rickandmortygraphql.domain.model.Character
import com.fappslab.rickandmortygraphql.domain.model.Characters
import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import com.fappslab.rickandmortygraphql.hubsrc.extension.toCharacters
import com.fappslab.rickandmortygraphql.remote.source.RickAndMortyDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RickAndMortyRepositoryImpl(
    private val remoteDataSource: RickAndMortyDataSource
) : RickAndMortyRepository {

    override fun getCharacters(page: Int): Flow<Characters> =
        remoteDataSource.getCharacters(page).map { it.characters.toCharacters() }
}
