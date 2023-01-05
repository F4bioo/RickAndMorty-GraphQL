package com.fappslab.rickandmortygraphql.remote.client.network.extension

import com.apollographql.apollo3.exception.ApolloHttpException
import com.fappslab.rickandmortygraphql.remote.client.network.exception.RemoteThrowable
import com.fappslab.rickandmortygraphql.remote.client.network.exception.RemoteThrowable.ClientThrowable
import com.fappslab.rickandmortygraphql.remote.client.network.exception.RemoteThrowable.ServerThrowable
import com.fappslab.rickandmortygraphql.remote.client.network.exception.RemoteThrowable.UnknownThrowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

@Suppress("MagicNumber")
private fun ApolloHttpException.parseError(): RemoteThrowable {
    return when (statusCode) {
        in 400..499 -> ClientThrowable(throwable = this)
        in 500..599 -> ServerThrowable(throwable = this)
        else -> UnknownThrowable(throwable = this)
    }
}

private fun Throwable.toThrowable(): RemoteThrowable {
    return when (this) {
        is ApolloHttpException -> parseError()
        else -> UnknownThrowable(throwable = this)
    }
}

fun <T> Flow<T>.orParseHttpError(): Flow<T> =
    catch { throwable ->
        throw throwable.toThrowable()
    }
