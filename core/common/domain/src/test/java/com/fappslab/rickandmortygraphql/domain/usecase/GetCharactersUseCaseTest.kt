package com.fappslab.rickandmortygraphql.domain.usecase

import app.cash.turbine.test
import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import stub.characterStub
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class GetCharactersUseCaseTest {

    private val repository: RickAndMortyRepository = mockk()
    private lateinit var subject: GetCharactersUseCase

    @Before
    fun setUp() {
        subject = GetCharactersUseCase(repository::getCharacters)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getCharactersSuccess Should return expected result When invoke subject`() {
        // Given
        val expectedCharacter = characterStub()
        every { repository.getCharacters(any()) } returns flowOf(expectedCharacter)

        // When
        val result = subject(1)

        // Then
        runTest {
            verify { repository.getCharacters(any()) }
            result.test {
                assertEquals(expectedCharacter, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `getCharactersFailure Should throw expected throwable When invoke subject`() {
        // Given
        val expectedThrowable = Throwable("Error message.")
        every { repository.getCharacters(any()) } returns flow { throw expectedThrowable }

        // When
        val result = subject(1)

        // Then
        runTest {
            verify { repository.getCharacters(any()) }
            result.test {
                assertEquals(expectedThrowable.message, awaitError().message)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
