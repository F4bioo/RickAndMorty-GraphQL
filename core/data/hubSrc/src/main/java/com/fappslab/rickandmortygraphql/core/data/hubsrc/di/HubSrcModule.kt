package com.fappslab.rickandmortygraphql.core.data.hubsrc.di

import android.content.SharedPreferences
import com.apollographql.apollo3.ApolloClient
import com.fappslab.rickandmortygraphql.core.data.local.prefs.LocalClient
import com.fappslab.rickandmortygraphql.core.common.domain.repository.RickAndMortyRepository
import com.fappslab.rickandmortygraphql.core.data.hubsrc.repository.RickAndMortyRepositoryImpl
import com.fappslab.rickandmortygraphql.core.data.hubsrc.source.local.LocalRickAndMortyDataSourceImpl
import com.fappslab.rickandmortygraphql.core.data.hubsrc.source.remote.RemoteRickAndMortyDataSourceImpl
import com.fappslab.rickandmortygraphql.core.data.remote.client.network.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

object HubSrcModule {

    val modules
        get() = listOf(dataModule)

    private val dataModule: Module = module {
        factory<RickAndMortyRepository> {
            RickAndMortyRepositoryImpl(
                localDataSource = LocalRickAndMortyDataSourceImpl(
                    prefs = get<LocalClient<SharedPreferences>>().create()
                ),
                remoteDataSource = RemoteRickAndMortyDataSourceImpl(
                    client = get<HttpClient<ApolloClient>>().create()
                )
            )
        }
    }
}
