package com.fappslab.rickandmortygraphql

import android.app.Application
import com.fappslab.rickandmortygraphql.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(appDeclaration = appDeclaration())
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }

    private fun appDeclaration(): KoinAppDeclaration = {
        androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
        androidContext(androidContext = this@Application)

        // Koin Load
        loadKoinModules(modules = AppModule.modules)
    }
}
