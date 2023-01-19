package com.fappslab.rickandmortygraphql.core.data.remote.client

import com.apollographql.apollo3.ApolloClient
import com.fappslab.rickandmortygraphql.core.data.remote.client.network.HttpClient

class HttpClientImpl(
    private val apollo: Apollo
) : HttpClient<ApolloClient> {

    override fun create(): ApolloClient {
        return apollo.create()
    }
}
