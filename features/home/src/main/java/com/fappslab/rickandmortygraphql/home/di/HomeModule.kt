package com.fappslab.rickandmortygraphql.home.di

import com.apollographql.apollo3.ApolloClient
import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import com.fappslab.rickandmortygraphql.domain.usecase.GetCharactersUseCase
import com.fappslab.rickandmortygraphql.home.presentation.viewmodel.HomeViewModel
import com.fappslab.rickandmortygraphql.hubsrc.repository.RickAndMortyRepositoryImpl
import com.fappslab.rickandmortygraphql.hubsrc.source.remote.RickAndMortyDataSourceImpl
import com.fappslab.rickandmortygraphql.remote.client.network.HttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

object HomeModule {

    val modules
        get() = listOf(presentationModule)

    private val presentationModule: Module = module {
        viewModel {
            HomeViewModel(
                getCharactersUseCase = GetCharactersUseCase(
                    getRickAndMortyRepository()::getCharacters
                )
            )
        }
    }

    private fun Scope.getRickAndMortyRepository(): RickAndMortyRepository =
        RickAndMortyRepositoryImpl(
            remoteDataSource = RickAndMortyDataSourceImpl(
                client = get<HttpClient<ApolloClient>>().create()
            )
        )
}
