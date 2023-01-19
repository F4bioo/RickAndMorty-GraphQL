package com.fappslab.rickandmortygraphql.features.home.di

import com.fappslab.rickandmortygraphql.libraries.arch.rules.DispatcherTestRule
import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import com.fappslab.rickandmortygraphql.features.home.di.HomeModule
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules

internal class HomeModuleTest : AutoCloseKoinTest() {

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private val networkModules = module {
        factory<RickAndMortyRepository> { mockk(relaxed = true) }
    }

    @Test
    fun `checkModules Should Koin provides dependencies When invoke HomeModule`() {
        startKoin {
            modules(HomeModule.modules + networkModules)
        }.checkModules()
    }
}
