package com.fappslab.rickandmortygraphql.core.data.remote.client.network.exception

// Those messages won't be used, they are for mock and tests.

const val CLIENT_DEFAULT_ERROR_MESSAGE =
    "Please check the internet connection and try again."

const val SERVER_DEFAULT_ERROR_MESSAGE =
    "Something went wrong on our end. Please try again later."

const val FILTER_DEFAULT_ERROR_MESSAGE =
    "No results. Please adjust filter or turn off it for more options."

const val UNKNOWN_DEFAULT_ERROR_MESSAGE =
    "There was an error processing your request. Please try again."

sealed class RemoteThrowable(cause: Throwable?) : Throwable(cause) {

    class ClientThrowable(
        override val message: String? = CLIENT_DEFAULT_ERROR_MESSAGE,
        throwable: Throwable? = null
    ) : RemoteThrowable(throwable)

    class ServerThrowable(
        override val message: String? = SERVER_DEFAULT_ERROR_MESSAGE,
        throwable: Throwable? = null
    ) : RemoteThrowable(throwable)

    class FilterThrowable(
        override val message: String? = FILTER_DEFAULT_ERROR_MESSAGE,
        throwable: Throwable? = null
    ) : RemoteThrowable(throwable)

    data class UnknownThrowable(
        override val message: String? = UNKNOWN_DEFAULT_ERROR_MESSAGE,
        val throwable: Throwable? = null
    ) : RemoteThrowable(throwable)
}
