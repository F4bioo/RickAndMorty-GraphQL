package com.fappslab.rickandmortygraphql.filter.di

import com.fappslab.rickandmortygraphql.filter.data.local.DataPrefs
import com.fappslab.rickandmortygraphql.filter.data.local.DataPrefsImpl
import com.fappslab.rickandmortygraphql.filter.data.repository.FilterRepositoryImpl
import com.fappslab.rickandmortygraphql.filter.data.source.FilterDataSourceImpl
import com.fappslab.rickandmortygraphql.filter.domain.repository.FilterRepository
import com.fappslab.rickandmortygraphql.filter.domain.usecase.DeleteFilterUseCase
import com.fappslab.rickandmortygraphql.filter.domain.usecase.GetFilterUseCase
import com.fappslab.rickandmortygraphql.filter.domain.usecase.SetFilterUseCase
import com.fappslab.rickandmortygraphql.filter.navigation.FilterNavigationImpl
import com.fappslab.rickandmortygraphql.filter.presentation.viewmodel.FilterViewModel
import com.fappslab.rickandmortygraphql.navigation.FilterNavigation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

object FilterModule {

    val modules
        get() = listOf(dataModule, presentationModule, additionalModule)

    private val dataModule: Module = module {
        single<DataPrefs> { DataPrefsImpl(context = get()) }
    }

    private val presentationModule: Module = module {
        viewModel {
            FilterViewModel(
                getFilterUseCase = GetFilterUseCase(
                    repository = getFilterRepository()
                ),
                setFilterUseCase = SetFilterUseCase(
                    repository = getFilterRepository()
                ),
                deleteFilterUseCase = DeleteFilterUseCase(
                    repository = getFilterRepository()
                )
            )
        }
    }

    private val additionalModule: Module = module {
        factory<FilterNavigation> { FilterNavigationImpl() }
    }

    private fun Scope.getFilterRepository(): FilterRepository =
        FilterRepositoryImpl(
            dataSource = FilterDataSourceImpl(
                prefs = get()
            )
        )
}
