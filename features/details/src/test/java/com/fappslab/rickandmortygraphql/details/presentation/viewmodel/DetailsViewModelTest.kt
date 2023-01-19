package com.fappslab.rickandmortygraphql.details.presentation.viewmodel

import app.cash.turbine.test
import com.fappslab.rickandmortygraphql.libraries.arch.rules.DispatcherTestRule
import com.fappslab.rickandmortygraphql.details.presentation.model.StatusType
import com.fappslab.rickandmortygraphql.details.stub.characterArgsStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class DetailsViewModelTest {

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private val args = characterArgsStub()
    private val initialState = DetailsViewState(args)

    private lateinit var subject: DetailsViewModel

    @Test
    fun `getStateAlive Should update type status When receive a Alive status`() {
        // Given
        val args = args.copy(status = "Alive")
        val expectedState = initialState.copy(statusType = StatusType.Alive, character = args)

        // When
        subject = DetailsViewModel(args)

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `getStateDead Should update type status When receive a Dead status`() {
        // Given
        val args = args.copy(status = "Dead")
        val expectedState = initialState.copy(statusType = StatusType.Dead, character = args)

        // When
        subject = DetailsViewModel(args)

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `getStateUnknown Should update type status When receive a Unknown status`() {
        // Given
        val args = args.copy(status = "unknown")
        val expectedState = initialState.copy(statusType = StatusType.Unknown, character = args)

        // When
        subject = DetailsViewModel(args)

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
