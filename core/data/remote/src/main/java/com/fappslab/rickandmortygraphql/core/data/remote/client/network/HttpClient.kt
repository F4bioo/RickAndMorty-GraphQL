package com.fappslab.rickandmortygraphql.core.data.remote.client.network

interface HttpClient<T> {
    fun create(): T
}
