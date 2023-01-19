package com.fappslab.rickandmortygraphql.features.details.di

import com.fappslab.rickandmortygraphql.features.details.presentation.viewmodel.DetailsViewModel
import com.fappslab.rickandmortygraphql.features.details.stub.characterArgsStub
import com.fappslab.rickandmortygraphql.features.details.di.DetailsModule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules

internal class DetailsModuleTest : AutoCloseKoinTest() {

    @Test
    fun `checkModules Should Koin provides dependencies When invoke DetailsModule`() {
        startKoin {
            modules(DetailsModule.modules)
        }.checkModules {
            create<DetailsViewModel> { parametersOf(characterArgsStub()) }
        }
    }
}
