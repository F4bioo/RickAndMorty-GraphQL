package com.fappslab.rickandmortygraphql.features.filter.di

import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import com.fappslab.rickandmortygraphql.features.filter.di.FilterModule
import io.mockk.mockk
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules

internal class FilterModuleTest : AutoCloseKoinTest() {

    private val mockedModules = module {
        factory<RickAndMortyRepository> {
            mockk(relaxed = true)
        }
    }

    @Test
    fun `checkModules Should Koin provides dependencies When invoke FilterModule`() {
        startKoin {
            modules(FilterModule.modules + mockedModules)
        }.checkModules()
    }
}
