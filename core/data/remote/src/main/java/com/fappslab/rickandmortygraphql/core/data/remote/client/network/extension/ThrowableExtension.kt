package com.fappslab.rickandmortygraphql.core.data.remote.client.network.extension

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.fappslab.rickandmortygraphql.core.data.remote.client.network.exception.RemoteThrowable
import com.fappslab.rickandmortygraphql.core.data.remote.client.network.exception.RemoteThrowable.ClientThrowable
import com.fappslab.rickandmortygraphql.core.data.remote.client.network.exception.RemoteThrowable.ServerThrowable
import com.fappslab.rickandmortygraphql.core.data.remote.client.network.exception.RemoteThrowable.UnknownThrowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private const val CLIENT_START: Int = 400
private const val CLIENT_END: Int = 499
private const val SERVER_START: Int = 500
private const val SERVER_END: Int = 599

private fun ApolloHttpException.parseError(): RemoteThrowable = when (statusCode) {
    in CLIENT_START..CLIENT_END -> ClientThrowable(throwable = this)
    in SERVER_START..SERVER_END -> ServerThrowable(throwable = this)
    else -> UnknownThrowable(throwable = this)
}

private fun Throwable.toThrowable(): RemoteThrowable = when (this) {
    is ApolloHttpException -> parseError()
    is ApolloException -> ClientThrowable(throwable = this, message = this.message)
    else -> UnknownThrowable(throwable = this, message = this.message)
}

fun <D : Query.Data> ApolloCall<D>.orParseHttpError(): Flow<D> = toFlow().map { response ->
    response.takeIf { it.errors.isNullOrEmpty() }?.dataAssertNoErrors
        ?: throw ApolloException(message = response.errors?.first()?.message)
}.catch { cause ->
    throw cause.toThrowable()
}
