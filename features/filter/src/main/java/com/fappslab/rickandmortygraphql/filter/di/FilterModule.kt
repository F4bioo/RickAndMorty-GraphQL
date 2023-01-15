package com.fappslab.rickandmortygraphql.filter.di

import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import com.fappslab.rickandmortygraphql.domain.usecase.GetFilterUseCase
import com.fappslab.rickandmortygraphql.filter.domain.usecase.DeleteFilterUseCase
import com.fappslab.rickandmortygraphql.filter.domain.usecase.SetFilterUseCase
import com.fappslab.rickandmortygraphql.filter.navigation.FilterNavigationImpl
import com.fappslab.rickandmortygraphql.filter.presentation.viewmodel.FilterViewModel
import com.fappslab.rickandmortygraphql.navigation.FilterNavigation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object FilterModule {

    val modules
        get() = listOf(presentationModule, additionalModule)

    private val presentationModule: Module = module {
        viewModel {
            FilterViewModel(
                getFilterUseCase = GetFilterUseCase(
                    get<RickAndMortyRepository>()::getFilter
                ),
                setFilterUseCase = SetFilterUseCase(
                    repository = get()
                ),
                deleteFilterUseCase = DeleteFilterUseCase(
                    repository = get()
                )
            )
        }
    }

    private val additionalModule: Module = module {
        factory<FilterNavigation> { FilterNavigationImpl() }
    }
}
