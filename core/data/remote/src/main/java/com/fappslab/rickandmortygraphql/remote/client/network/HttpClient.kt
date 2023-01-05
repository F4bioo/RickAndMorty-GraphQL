package com.fappslab.rickandmortygraphql.remote.client.network

interface HttpClient<T> {
    fun create(): T
}
