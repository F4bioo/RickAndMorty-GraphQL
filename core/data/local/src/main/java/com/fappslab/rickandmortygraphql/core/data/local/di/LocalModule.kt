package com.fappslab.rickandmortygraphql.core.data.local.di

import android.content.SharedPreferences
import com.fappslab.rickandmortygraphql.core.data.local.BuildConfig
import com.fappslab.rickandmortygraphql.core.data.local.prefs.DataPrefs
import com.fappslab.rickandmortygraphql.core.data.local.prefs.LocalClient
import com.fappslab.rickandmortygraphql.core.data.local.prefs.LocalClientImpl
import org.koin.core.module.Module
import org.koin.dsl.module

object LocalModule {

    val modules
        get() = listOf(dataModule)

    private val dataModule: Module = module {
        single<LocalClient<SharedPreferences>> {
            LocalClientImpl(
                dataPrefs = DataPrefs(context = get(), prefsName = BuildConfig.PREFS_NAME)
            )
        }
    }
}
