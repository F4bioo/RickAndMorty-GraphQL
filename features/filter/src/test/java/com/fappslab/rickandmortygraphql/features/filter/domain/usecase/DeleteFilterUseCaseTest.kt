package com.fappslab.rickandmortygraphql.features.filter.domain.usecase

import app.cash.turbine.test
import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import com.fappslab.rickandmortygraphql.features.filter.domain.usecase.DeleteFilterUseCase
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
internal class DeleteFilterUseCaseTest {

    private val repository: RickAndMortyRepository = mockk()
    private lateinit var subject: DeleteFilterUseCase

    @Before
    fun setUp() {
        subject = DeleteFilterUseCase(
            repository = repository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `deleteFilter Should delete prefs value When invoke deleteFilter`() {
        // Given
        val expectedResult = Unit
        every { repository.deleteFilter(any()) } returns flowOf(expectedResult)

        // When
        val result = subject(prefsKey = "status")

        // Then
        runTest {
            result.test {
                assertEquals(expectedResult, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
            verify { repository.deleteFilter(any()) }
        }
    }
}
