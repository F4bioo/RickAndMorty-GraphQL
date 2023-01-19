package com.fappslab.rickandmortygraphql.features.home.di

import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import com.fappslab.rickandmortygraphql.domain.usecase.GetCharactersUseCase
import com.fappslab.rickandmortygraphql.domain.usecase.GetFilterUseCase
import com.fappslab.rickandmortygraphql.features.home.navigation.HomeNavigationImpl
import com.fappslab.rickandmortygraphql.features.home.presentation.viewmodel.HomeViewModel
import com.fappslab.rickandmortygraphql.navigation.HomeNavigation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object HomeModule {

    val modules
        get() = listOf(presentationModule, additionalModule)

    private val presentationModule: Module = module {
        viewModel {
            HomeViewModel(
                getFilterUseCase = GetFilterUseCase(
                    get<RickAndMortyRepository>()::getFilter
                ),
                getCharactersUseCase = GetCharactersUseCase(
                    get<RickAndMortyRepository>()::getCharactersFilter
                )
            )
        }
    }

    private val additionalModule: Module = module {
        factory<HomeNavigation> { HomeNavigationImpl() }
    }
}
