package com.fappslab.rickandmortygraphql.hubsrc.source.remote

import com.apollographql.apollo3.ApolloClient
import com.fappslab.rickandmortygraphql.remote.GetCharactersQuery
import com.fappslab.rickandmortygraphql.remote.client.network.extension.orParseHttpError
import com.fappslab.rickandmortygraphql.remote.source.RickAndMortyDataSource
import kotlinx.coroutines.flow.Flow

internal class RickAndMortyDataSourceImpl(
    private val client: ApolloClient
) : RickAndMortyDataSource {

    override fun getCharacters(page: Int): Flow<GetCharactersQuery.Data> =
        client.query(GetCharactersQuery(page)).orParseHttpError()

    override suspend fun getCharactersPagination(page: Int): GetCharactersQuery.Data? =
        client.query(GetCharactersQuery(page)).execute().data
}
