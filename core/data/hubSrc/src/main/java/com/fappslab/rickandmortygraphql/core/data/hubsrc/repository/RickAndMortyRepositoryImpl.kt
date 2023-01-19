package com.fappslab.rickandmortygraphql.core.data.hubsrc.repository

import com.fappslab.rickandmortygraphql.core.data.local.source.LocalRickAndMortyDataSource
import com.fappslab.rickandmortygraphql.domain.model.Characters
import com.fappslab.rickandmortygraphql.domain.model.Filter
import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import com.fappslab.rickandmortygraphql.core.data.hubsrc.extension.toCharacters
import com.fappslab.rickandmortygraphql.core.data.hubsrc.extension.toFilterCharacter
import com.fappslab.rickandmortygraphql.core.data.remote.source.RemoteRickAndMortyDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RickAndMortyRepositoryImpl(
    private val localDataSource: LocalRickAndMortyDataSource,
    private val remoteDataSource: RemoteRickAndMortyDataSource
) : RickAndMortyRepository {

    override fun getFilter(prefsKey: String): Flow<String?> =
        localDataSource.getFilter(prefsKey)

    override fun setFilter(prefsKey: String, value: String): Flow<Unit> =
        localDataSource.setFilter(prefsKey, value)

    override fun deleteFilter(prefsKey: String): Flow<Unit> =
        localDataSource.deleteFilter(prefsKey)

    override fun getCharactersFilter(filter: Filter): Flow<Characters> {
        return remoteDataSource.getCharactersFilter(filter.page, filter.toFilterCharacter())
            .map { it.characters.toCharacters() }
    }
}
