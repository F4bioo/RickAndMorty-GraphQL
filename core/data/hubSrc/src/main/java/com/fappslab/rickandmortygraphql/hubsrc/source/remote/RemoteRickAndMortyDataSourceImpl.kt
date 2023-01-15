package com.fappslab.rickandmortygraphql.hubsrc.source.remote

import com.apollographql.apollo3.ApolloClient
import com.fappslab.rickandmortygraphql.remote.GetCharactersFilterQuery
import com.fappslab.rickandmortygraphql.remote.client.network.extension.orParseHttpError
import com.fappslab.rickandmortygraphql.remote.source.RemoteRickAndMortyDataSource
import com.fappslab.rickandmortygraphql.remote.type.FilterCharacter
import kotlinx.coroutines.flow.Flow

class RemoteRickAndMortyDataSourceImpl(
    private val client: ApolloClient
) : RemoteRickAndMortyDataSource {

    override fun getCharactersFilter(
        page: Int,
        filter: FilterCharacter
    ): Flow<GetCharactersFilterQuery.Data> = client.query(
        GetCharactersFilterQuery(page, filter)
    ).orParseHttpError()
}
