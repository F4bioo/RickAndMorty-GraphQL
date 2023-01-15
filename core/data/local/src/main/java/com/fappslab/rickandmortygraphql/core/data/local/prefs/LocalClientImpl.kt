package com.fappslab.rickandmortygraphql.core.data.local.prefs

import android.content.SharedPreferences

class LocalClientImpl(
    private val dataPrefs: DataPrefs
) : LocalClient<SharedPreferences> {

    override fun create(): SharedPreferences {
        return dataPrefs.create()
    }
}
