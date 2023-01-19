package com.fappslab.rickandmortygraphql.features.filter.domain.usecase

import app.cash.turbine.test
import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import com.fappslab.rickandmortygraphql.features.filter.domain.usecase.SetFilterUseCase
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class SetFilterUseCaseTest {

    private val repository: RickAndMortyRepository = mockk()
    private lateinit var subject: SetFilterUseCase

    @Before
    fun setUp() {
        subject = SetFilterUseCase(
            repository = repository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `setFilter Should save prefs value When invoke setFilter`() {
        // Given
        val expectedResult = Unit
        every { repository.setFilter(any(), any()) } returns flowOf(expectedResult)

        // When
        val result = subject(prefsKey = "status", value = "alive")

        // Then
        runTest {
            result.test {
                assertEquals(expectedResult, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
            verify { repository.setFilter(any(), any()) }
        }
    }
}
