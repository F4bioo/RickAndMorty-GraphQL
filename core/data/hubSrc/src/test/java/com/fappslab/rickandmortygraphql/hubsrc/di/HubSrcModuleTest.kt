package com.fappslab.rickandmortygraphql.hubsrc.di

import android.content.SharedPreferences
import com.apollographql.apollo3.ApolloClient
import com.fappslab.rickandmortygraphql.core.data.local.prefs.LocalClient
import com.fappslab.rickandmortygraphql.core.data.local.prefs.LocalClientImpl
import com.fappslab.rickandmortygraphql.remote.client.HttpClientImpl
import com.fappslab.rickandmortygraphql.remote.client.network.HttpClient
import io.mockk.mockk
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules

internal class HubSrcModuleTest : AutoCloseKoinTest() {

    private val mockedModules = module {
        single<LocalClient<SharedPreferences>> {
            LocalClientImpl(mockk(relaxed = true))
        }
        single<HttpClient<ApolloClient>> {
            HttpClientImpl(mockk(relaxed = true))
        }
    }

    @Test
    fun `checkModules Should Koin provides dependencies When invoke LocalModule`() {
        startKoin {
            modules(HubSrcModule.modules + mockedModules)
        }.checkModules()
    }
}
