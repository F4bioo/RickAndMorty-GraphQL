package com.fappslab.rickandmortygraphql.home.presentation.viewmodel

import com.fappslab.rickandmortygraphql.arch.rules.DispatcherTestRule
import com.fappslab.rickandmortygraphql.arch.test.testFlows
import com.fappslab.rickandmortygraphql.arch.test.testStateFlow
import com.fappslab.rickandmortygraphql.design.R
import com.fappslab.rickandmortygraphql.domain.usecase.GetCharactersUseCase
import com.fappslab.rickandmortygraphql.hubsrc.utils.StubResponse.expectedSuccessDataResponse
import com.fappslab.rickandmortygraphql.hubsrc.utils.toCharacters
import com.fappslab.rickandmortygraphql.remote.client.network.exception.RemoteThrowable.ClientThrowable
import com.fappslab.rickandmortygraphql.remote.client.network.exception.RemoteThrowable.ServerThrowable
import com.fappslab.rickandmortygraphql.remote.client.network.exception.RemoteThrowable.UnknownThrowable
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@ExperimentalCoroutinesApi
internal class HomeViewModelTest {

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private val initialState = HomeViewState()

    private val getCharactersUseCase: GetCharactersUseCase = mockk()
    private lateinit var subject: HomeViewModel

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `initBlockSuccess Should update state When init block get success`() {
        // Given
        val characters = expectedSuccessDataResponse?.characters.toCharacters()
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState.copy(
            characters = characters.characters,
            totalPages = 42,
            nextPage = 2
        )
        val expectedFinalState = expectedSecondState.copy(shouldShowLoading = false)

        // When
        subject = setupSubjectWithSuccess()

        // Then
        runTest {
            verify { getCharactersUseCase(any()) }
            subject.testStateFlow(backgroundScope) {
                assertStateIs { initialState }
                assertStateIs { expectedFirstState }
                assertStateIs { expectedSecondState }
                assertStateIs { expectedFinalState }
            }
        }
    }

    @Test
    fun `initBlockFailure Should expose state When init block get ClientThrowable`() {
        // Given
        val expectedMessage = R.string.common_client_error
        val (expectedFirstState, expectedSecondState, expectedFinalState) = setupFailureStates()

        // When
        subject = setupSubjectWithFailure(throwable = ClientThrowable())

        // Then
        runTest {
            verify { getCharactersUseCase(any()) }
            subject.testFlows(backgroundScope) { action ->
                assertStateIs { initialState }
                assertStateIs { expectedFirstState }
                assertStateIs { expectedSecondState }
                assertStateIs { expectedFinalState }

                assertIs<HomeViewAction.Error>(action)
                assertEquals(expectedMessage, (action as? HomeViewAction.Error)?.message)
            }
        }
    }

    @Test
    fun `initBlockFailure Should expose state When init block get ServerThrowable`() {
        // Given
        val expectedMessage = R.string.common_server_error
        val (expectedFirstState, expectedSecondState, expectedFinalState) = setupFailureStates()

        // When
        subject = setupSubjectWithFailure(throwable = ServerThrowable())

        // Then
        runTest {
            verify { getCharactersUseCase(any()) }
            subject.testFlows(backgroundScope) { action ->
                assertStateIs { initialState }
                assertStateIs { expectedFirstState }
                assertStateIs { expectedSecondState }
                assertStateIs { expectedFinalState }

                assertIs<HomeViewAction.Error>(action)
                assertEquals(expectedMessage, (action as? HomeViewAction.Error)?.message)
            }
        }
    }

    @Test
    fun `initBlockFailure Should expose state When init block get UnknownThrowable`() {
        // Given
        val expectedMessage = R.string.common_unknown_error
        val (expectedFirstState, expectedSecondState, expectedFinalState) = setupFailureStates()

        // When
        subject = setupSubjectWithFailure(throwable = UnknownThrowable())

        // Then
        runTest {
            verify { getCharactersUseCase(any()) }
            subject.testFlows(backgroundScope) { action ->
                assertStateIs { initialState }
                assertStateIs { expectedFirstState }
                assertStateIs { expectedSecondState }
                assertStateIs { expectedFinalState }

                assertIs<HomeViewAction.Error>(action)
                assertEquals(expectedMessage, (action as? HomeViewAction.Error)?.message)
            }
        }
    }

    @Test
    fun `onPagination Should fetch next page When shouldFetchNextPage is true`() {
        // Given
        runTest { subject = setupSubjectWithSuccess() }

        // When
        subject.onPagination(shouldFetchNextPage = true)

        // Then
        verify(exactly = 2) { getCharactersUseCase(any()) }
    }

    @Test
    fun `onPagination Should not fetch next page When shouldFetchNextPage is false`() {
        // Given
        runTest { subject = setupSubjectWithSuccess() }

        // When
        subject.onPagination(shouldFetchNextPage = false)

        // Then
        verify { getCharactersUseCase(any()) }
    }

    @Test
    fun `onFabVisibility Should update shouldFetchNextPage state When invoke onPagination`() {
        // Given
        val expectedState = initialState.copy(shouldShowFabButton = false)
        subject = setupSubjectWithSuccess()

        // When
        subject.onFabVisibility(shouldShowFabButton = false)

        // Then
        runTest {
            verify { getCharactersUseCase(any()) }
            subject.testStateFlow(backgroundScope) {
                assertStateIs { expectedState }
            }
        }
    }

    @Test
    fun `onShowDetails Should update state When invoke method to show details`() {
        // Given
        val character = expectedSuccessDataResponse?.characters.toCharacters().characters.first()
        val expectedState = initialState.copy(shouldShowDetails = true, character = character)
        subject = setupSubjectWithSuccess()

        // When
        subject.onShowDetails(character)

        // Then
        runTest {
            subject.testStateFlow(backgroundScope) {
                assertStateIs { expectedState }
            }
        }
    }

    @Test
    fun `onCloseDetails Should update state When invoke method to close details`() {
        // Given
        val expectedState = initialState.copy(shouldShowDetails = false)
        subject = setupSubjectWithSuccess()

        // When
        subject.onCloseDetails()

        // Then
        runTest {
            subject.testStateFlow(backgroundScope) {
                assertStateIs { expectedState }
            }
        }
    }

    private fun setupSubjectWithFailure(throwable: Throwable): HomeViewModel {
        every { getCharactersUseCase(any()) } returns flow { throw throwable }
        return HomeViewModel(
            getCharactersUseCase = getCharactersUseCase,
            dispatcher = dispatcherRule.testDispatcher
        )
    }

    private fun setupSubjectWithSuccess(): HomeViewModel {
        val characters = expectedSuccessDataResponse?.characters.toCharacters()
        every { getCharactersUseCase(any()) } returns flowOf(characters)

        return HomeViewModel(
            getCharactersUseCase = getCharactersUseCase,
            dispatcher = dispatcherRule.testDispatcher
        )
    }

    private fun setupFailureStates(): Triple<HomeViewState, HomeViewState, HomeViewState> {
        val expectedFirstState = initialState.copy(
            shouldShowLoading = true,
            shouldShowTryAgain = false
        )
        val expectedSecondState = expectedFirstState.copy(
            shouldShowLoading = false
        )
        val expectedFinalState = initialState.copy(
            shouldShowEmptyLayout = true,
            shouldShowTryAgain = true
        )
        return Triple(expectedFirstState, expectedSecondState, expectedFinalState)
    }
}
