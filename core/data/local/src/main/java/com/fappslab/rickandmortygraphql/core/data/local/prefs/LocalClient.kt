package com.fappslab.rickandmortygraphql.core.data.local.prefs

interface LocalClient<T> {
    fun create(): T
}
