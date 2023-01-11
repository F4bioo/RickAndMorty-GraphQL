package com.fappslab.rickandmortygraphql.details.di

import com.fappslab.rickandmortygraphql.details.navigation.DetailsNavigationImpl
import com.fappslab.rickandmortygraphql.details.presentation.viewmodel.DetailsViewModel
import com.fappslab.rickandmortygraphql.navigation.CharacterArgs
import com.fappslab.rickandmortygraphql.navigation.DetailsNavigation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object DetailsModule {

    val modules
        get() = listOf(presentationModule, additionalModule)

    private val presentationModule: Module = module {
        viewModel { (args: CharacterArgs) ->
            DetailsViewModel(args)
        }
    }

    private val additionalModule: Module = module {
        factory<DetailsNavigation> { DetailsNavigationImpl() }
    }
}
