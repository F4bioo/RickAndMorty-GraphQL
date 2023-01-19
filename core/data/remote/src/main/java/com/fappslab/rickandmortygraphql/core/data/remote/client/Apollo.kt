package com.fappslab.rickandmortygraphql.core.data.remote.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.fappslab.rickandmortygraphql.core.data.remote.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

private const val READ_TIMEOUT = 15L
private const val WRITE_TIMEOUT = 10L
private const val CONNECT_TIMEOUT = 15L

class Apollo(private val baseUrl: String) {

    fun create(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(baseUrl)
            .okHttpClient(provideOKHttpClient())
            .build()
    }

    private fun provideOKHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor())
            .build()

    private fun httpLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else HttpLoggingInterceptor.Level.NONE
        }
}
