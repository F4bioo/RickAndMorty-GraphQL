package com.fappslab.rickandmortygraphql.hubsrc.repository

import app.cash.turbine.test
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.mockserver.MockServer
import com.apollographql.apollo3.mockserver.enqueue
import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import com.fappslab.rickandmortygraphql.hubsrc.source.remote.RickAndMortyDataSourceImpl
import com.fappslab.rickandmortygraphql.hubsrc.stub.filterStub
import com.fappslab.rickandmortygraphql.hubsrc.utils.StubResponse.expectedFailureBodyResponse
import com.fappslab.rickandmortygraphql.hubsrc.utils.StubResponse.expectedSuccessBodyResponse
import com.fappslab.rickandmortygraphql.hubsrc.utils.StubResponse.expectedSuccessDataResponse
import com.fappslab.rickandmortygraphql.hubsrc.utils.toCharacters
import com.fappslab.rickandmortygraphql.remote.client.network.exception.CLIENT_DEFAULT_ERROR_MESSAGE
import com.fappslab.rickandmortygraphql.remote.client.network.exception.SERVER_DEFAULT_ERROR_MESSAGE
import com.fappslab.rickandmortygraphql.remote.client.network.exception.UNKNOWN_DEFAULT_ERROR_MESSAGE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_MULT_CHOICE
import java.net.HttpURLConnection.HTTP_OK
import kotlin.test.assertEquals

@ApolloExperimental
@ExperimentalCoroutinesApi
internal class RickAndMortyRepositoryImplIntegrationTest {

    private val mockServer = MockServer()

    private lateinit var apolloClient: ApolloClient
    private lateinit var subject: RickAndMortyRepository

    @Before
    fun setUp() {
        runTest {
            apolloClient = ApolloClient.Builder()
                .serverUrl(mockServer.url())
                .build()
        }

        subject = RickAndMortyRepositoryImpl(
            remoteDataSource = RickAndMortyDataSourceImpl(
                client = apolloClient
            )
        )
    }

    @After
    fun tearDown() {
        runTest {
            mockServer.stop()
        }
    }

    @Test
    fun `getRemoteDataSuccess Should return success response When invoke getCharactersFilter`() {
        // Given
        val expectedResult = expectedSuccessDataResponse?.characters.toCharacters()
        mockServer.enqueue(expectedSuccessBodyResponse, statusCode = HTTP_OK)

        // When
        val result = subject.getCharactersFilter(filter = filterStub())

        // Then
        runTest {
            result.test {
                assertEquals(expectedResult, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `getRemoteDataFailure Should return failure message from backend When invoke getCharactersFilter`() {
        // Given
        val expectedMessage = "Error message."
        mockServer.enqueue(expectedFailureBodyResponse, statusCode = HTTP_OK)

        // When
        val result = subject.getCharactersFilter(filter = filterStub())

        // Then
        runTest {
            result.test {
                assertEquals(expectedMessage, awaitError().message)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `getRemoteDataFailure Should return client side failure message When invoke getCharactersFilter`() {
        // Given
        val expectedMessage = CLIENT_DEFAULT_ERROR_MESSAGE
        mockServer.enqueue(string = "{}", statusCode = HTTP_BAD_REQUEST)

        // When
        val result = subject.getCharactersFilter(filter = filterStub())

        // Then
        runTest {
            result.test {
                assertEquals(expectedMessage, awaitError().message)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `getRemoteDataFailure Should return server side failure message When invoke getCharactersFilter`() {
        // Given
        val expectedMessage = SERVER_DEFAULT_ERROR_MESSAGE
        mockServer.enqueue(string = "{}", statusCode = HTTP_INTERNAL_ERROR)

        // When
        val result = subject.getCharactersFilter(filter = filterStub())

        // Then
        runTest {
            result.test {
                assertEquals(expectedMessage, awaitError().message)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `getRemoteDataFailure Should return generic failure message When invoke getCharactersFilter`() {
        // Given
        val expectedMessage = UNKNOWN_DEFAULT_ERROR_MESSAGE
        mockServer.enqueue(string = "{}", statusCode = HTTP_MULT_CHOICE)

        // When
        val result = subject.getCharactersFilter(filter = filterStub())

        // Then
        runTest {
            result.test {
                assertEquals(expectedMessage, awaitError().message)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
