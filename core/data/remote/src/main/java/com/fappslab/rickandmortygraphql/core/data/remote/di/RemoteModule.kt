package com.fappslab.rickandmortygraphql.core.data.remote.di

import com.apollographql.apollo3.ApolloClient
import com.fappslab.rickandmortygraphql.remote.BuildConfig
import com.fappslab.rickandmortygraphql.core.data.remote.client.Apollo
import com.fappslab.rickandmortygraphql.core.data.remote.client.network.HttpClient
import com.fappslab.rickandmortygraphql.core.data.remote.client.HttpClientImpl
import org.koin.core.module.Module
import org.koin.dsl.module

object RemoteModule {

    val modules
        get() = listOf(dataModule)

    private val dataModule: Module = module {
        single<HttpClient<ApolloClient>> {
            HttpClientImpl(
                apollo = Apollo(baseUrl = BuildConfig.BASE_URL)
            )
        }
    }
}
