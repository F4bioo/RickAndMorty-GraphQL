package com.fappslab.rickandmortygraphql.home.di

import com.fappslab.rickandmortygraphql.arch.rules.DispatcherTestRule
import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
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
