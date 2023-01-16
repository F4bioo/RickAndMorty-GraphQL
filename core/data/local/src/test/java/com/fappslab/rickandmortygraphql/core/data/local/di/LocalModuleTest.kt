package com.fappslab.rickandmortygraphql.core.data.local.di

import android.content.Context
import io.mockk.mockk
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules

internal class LocalModuleTest : AutoCloseKoinTest() {

    private val mockedModules = module {
        factory { mockk<Context>(relaxed = true) }
    }

    @Test
    fun `checkModules Should Koin provides dependencies When invoke LocalModule`() {
        startKoin {
            modules(LocalModule.modules + mockedModules)
        }.checkModules()
    }
}
