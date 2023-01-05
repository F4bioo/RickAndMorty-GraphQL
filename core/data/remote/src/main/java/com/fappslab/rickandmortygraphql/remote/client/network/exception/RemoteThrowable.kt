package com.fappslab.rickandmortygraphql.remote.client.network.exception

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

internal const val CLIENT_DEFAULT_ERROR_MESSAGE =
    "Please check the internet connection and try again."

internal const val SERVER_DEFAULT_ERROR_MESSAGE =
    "Something went wrong on our end. Please try again later."

internal const val UNKNOWN_DEFAULT_ERROR_MESSAGE =
    "There was an error processing your request. Please try again."

sealed class RemoteThrowable(cause: Throwable?) : Throwable(cause) {

    class ClientThrowable(
        @Expose override val message: String? = CLIENT_DEFAULT_ERROR_MESSAGE,
        @Expose private val throwable: Throwable? = null
    ) : RemoteThrowable(throwable)

    class ServerThrowable(
        @Expose override val message: String? = SERVER_DEFAULT_ERROR_MESSAGE,
        @Expose private val throwable: Throwable? = null
    ) : RemoteThrowable(throwable)

    data class UnknownThrowable(
        @SerializedName("message") override val message: String? = UNKNOWN_DEFAULT_ERROR_MESSAGE,
        @Expose private val throwable: Throwable? = null
    ) : RemoteThrowable(throwable)
}
