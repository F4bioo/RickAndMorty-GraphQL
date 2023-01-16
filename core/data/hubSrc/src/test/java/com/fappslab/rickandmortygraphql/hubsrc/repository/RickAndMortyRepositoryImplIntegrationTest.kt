package com.fappslab.rickandmortygraphql.hubsrc.repository

import android.content.Context
import android.content.SharedPreferences
import app.cash.turbine.test
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.mockserver.MockServer
import com.apollographql.apollo3.mockserver.enqueue
import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import com.fappslab.rickandmortygraphql.hubsrc.source.local.LocalRickAndMortyDataSourceImpl
import com.fappslab.rickandmortygraphql.hubsrc.source.remote.RemoteRickAndMortyDataSourceImpl
import com.fappslab.rickandmortygraphql.hubsrc.stub.filterStub
import com.fappslab.rickandmortygraphql.hubsrc.utils.StubResponse.expectedFailureBodyResponse
import com.fappslab.rickandmortygraphql.hubsrc.utils.StubResponse.expectedSuccessBodyResponse
import com.fappslab.rickandmortygraphql.hubsrc.utils.StubResponse.expectedSuccessDataResponse
import com.fappslab.rickandmortygraphql.hubsrc.utils.toCharacters
import com.fappslab.rickandmortygraphql.remote.client.network.exception.CLIENT_DEFAULT_ERROR_MESSAGE
import com.fappslab.rickandmortygraphql.remote.client.network.exception.SERVER_DEFAULT_ERROR_MESSAGE
import com.fappslab.rickandmortygraphql.remote.client.network.exception.UNKNOWN_DEFAULT_ERROR_MESSAGE
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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

    private val context = mockk<Context>(relaxed = true)
    private val prefs = mockk<SharedPreferences>(relaxed = true)
    private val prefsEditor = mockk<SharedPreferences.Editor>(relaxed = true)
    private lateinit var apolloClient: ApolloClient
    private lateinit var subject: RickAndMortyRepository

    @Before
    fun setUp() {
        every { context.getSharedPreferences(any(), any()) } returns prefs
        every { prefs.edit() } returns prefsEditor

        runTest {
            apolloClient = ApolloClient.Builder()
                .serverUrl(mockServer.url())
                .build()
        }

        subject = RickAndMortyRepositoryImpl(
            localDataSource = LocalRickAndMortyDataSourceImpl(
                prefs = prefs
            ),
            remoteDataSource = RemoteRickAndMortyDataSourceImpl(
                client = apolloClient
            )
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
        runTest { mockServer.stop() }
    }

    @Test
    fun `getFilter Should get string filter value from shared preferences When invoke getFilter`() {
        val expectedResult = "alive"
        every { prefs.getString(any(), any()) } returns expectedResult

        // When
        val result = subject.getFilter(prefsKey = "prefsKey")

        // Then
        runTest {
            assertEquals(expectedResult, result.first())
            verify { prefs.getString(any(), any()) }
        }
    }

    @Test
    fun `setFilter Should save string filter value in shared preferences When invoke setFilter`() {
        val value = "alive"
        val expectedResult = Unit
        every { prefsEditor.putString(any(), any()) } returns prefsEditor

        // When
        val result = subject.setFilter(prefsKey = "prefsKey", value = value)

        // Then
        runTest {
            assertEquals(expectedResult, result.first())
            verify { prefsEditor.putString(any(), any()) }
        }
    }

    @Test
    fun `deleteFilter Should delete string filter from shared preferences When invoke deleteFilter`() {
        val expectedResult = Unit
        every { prefsEditor.remove(any()) } returns prefsEditor

        // When
        val result = subject.deleteFilter(prefsKey = "prefsKey")

        // Then
        runTest {
            assertEquals(expectedResult, result.first())
            verify { prefsEditor.remove(any()) }
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
