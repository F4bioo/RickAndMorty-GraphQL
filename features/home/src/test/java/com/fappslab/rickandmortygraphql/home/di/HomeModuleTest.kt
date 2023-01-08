package com.fappslab.rickandmortygraphql.home.di

import com.apollographql.apollo3.ApolloClient
import com.fappslab.rickandmortygraphql.arch.rules.DispatcherTestRule
import com.fappslab.rickandmortygraphql.remote.client.HttpClientImpl
import com.fappslab.rickandmortygraphql.remote.client.network.HttpClient
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
        single<HttpClient<ApolloClient>> {
            HttpClientImpl(mockk(relaxed = true))
        }
    }

    @Test
    fun `checkModules Should Koin provides dependencies When invoke HomeModule`() {
        startKoin {
            modules(HomeModule.modules + networkModules)
        }.checkModules()
    }
}
