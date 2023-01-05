package com.fappslab.rickandmortygraphql.remote.network.di

import com.fappslab.rickandmortygraphql.remote.di.RemoteModule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules

internal class RemoteModuleTest : AutoCloseKoinTest() {

    @Test
    fun `checkModules Should Koin provides dependencies When invoke RemoteModule`() {
        startKoin {
            modules(RemoteModule.modules)
        }.checkModules()
    }
}
