package com.fappslab.rickandmortygraphql.home.presentation.viewmodel

import com.fappslab.rickandmortygraphql.libraries.arch.rules.DispatcherTestRule
import com.fappslab.rickandmortygraphql.libraries.arch.test.testActionFlow
import com.fappslab.rickandmortygraphql.libraries.arch.test.testFlows
import com.fappslab.rickandmortygraphql.libraries.arch.test.testStateFlow
import com.fappslab.rickandmortygraphql.design.R
import com.fappslab.rickandmortygraphql.domain.model.Characters
import com.fappslab.rickandmortygraphql.domain.model.Filter
import com.fappslab.rickandmortygraphql.domain.usecase.GetCharactersUseCase
import com.fappslab.rickandmortygraphql.domain.usecase.GetFilterUseCase
import com.fappslab.rickandmortygraphql.hubsrc.utils.StubResponse.expectedSuccessDataResponse
import com.fappslab.rickandmortygraphql.hubsrc.utils.toCharacters
import com.fappslab.rickandmortygraphql.remote.client.network.exception.RemoteThrowable.ClientThrowable
import com.fappslab.rickandmortygraphql.remote.client.network.exception.RemoteThrowable.FilterThrowable
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

    private val getFilterUseCase: GetFilterUseCase = mockk()
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
        val expectedFirstState = initialState.copy(
            shouldShowLoading = true,
            shouldShowTryAgain = false
        )
        val expectedSecondState = expectedFirstState.copy(
            characters = characters.characters,
            filter = Filter(page = 2),
            totalPages = 42
        )
        val expectedFinalState = expectedSecondState.copy(shouldShowLoading = false)

        // When
        subject = createSubjectWithInitSuccess(characters)

        runTest {
            // Then
            subject.testStateFlow(backgroundScope) {
                assertStateIs { initialState }
                assertStateIs { expectedFirstState }
                assertStateIs { expectedSecondState }
                assertStateIs { expectedFinalState }
            }
            verify { getFilterUseCase(any()) }
            verify { getCharactersUseCase(any()) }
        }
    }

    @Test
    fun `initBlockSuccess Should expose Error action When init block get success with empty list`() {
        // Given
        val characters = Characters(characters = emptyList(), totalPages = 0, nextPage = 0)
        val expectedMessage = R.string.common_filter_error
        val expectedFirstState = initialState.copy(
            shouldShowLoading = true,
            shouldShowTryAgain = false
        )
        val expectedFinalState = expectedFirstState.copy(shouldShowLoading = false)

        // When
        subject = createSubjectWithInitSuccess(characters)

        runTest {
            // Then
            subject.testFlows(backgroundScope) { action ->
                assertStateIs { initialState }
                assertStateIs { expectedFirstState }
                assertStateIs { expectedFinalState }

                assertIs<HomeViewAction.Error>(action)
                assertEquals(expectedMessage, (action as? HomeViewAction.Error)?.message)
            }
            verify { getFilterUseCase(any()) }
            verify { getCharactersUseCase(any()) }
        }
    }

    @Test
    fun `initBlockFailure Should expose state When init block get ClientThrowable`() {
        // Given
        val expectedMessage = R.string.common_client_error
        val (expectedFirstState, expectedSecondState, expectedFinalState) = setupFailureStates()

        // When
        subject = createSubjectWithInitFailure(ClientThrowable())

        // Then
        runTest {
            subject.testFlows(backgroundScope) { action ->
                assertStateIs { initialState }
                assertStateIs { expectedFirstState }
                assertStateIs { expectedSecondState }
                assertStateIs { expectedFinalState }

                assertIs<HomeViewAction.Error>(action)
                assertEquals(expectedMessage, (action as? HomeViewAction.Error)?.message)
            }
            verify { getFilterUseCase(any()) }
            verify { getCharactersUseCase(any()) }
        }
    }

    @Test
    fun `initBlockFailure Should expose state When init block get ServerThrowable`() {
        // Given
        val expectedMessage = R.string.common_server_error
        val (expectedFirstState, expectedSecondState, expectedFinalState) = setupFailureStates()

        // When
        subject = createSubjectWithInitFailure(ServerThrowable())

        // Then
        runTest {
            subject.testFlows(backgroundScope) { action ->
                assertStateIs { initialState }
                assertStateIs { expectedFirstState }
                assertStateIs { expectedSecondState }
                assertStateIs { expectedFinalState }

                assertIs<HomeViewAction.Error>(action)
                assertEquals(expectedMessage, (action as? HomeViewAction.Error)?.message)
            }
            verify { getFilterUseCase(any()) }
            verify { getCharactersUseCase(any()) }
        }
    }

    @Test
    fun `initBlockFailure Should expose state When init block get UnknownThrowable`() {
        // Given
        val expectedMessage = R.string.common_unknown_error
        val (expectedFirstState, expectedSecondState, expectedFinalState) = setupFailureStates()

        // When
        subject = createSubjectWithInitFailure(UnknownThrowable())

        // Then
        runTest {
            subject.testFlows(backgroundScope) { action ->
                assertStateIs { initialState }
                assertStateIs { expectedFirstState }
                assertStateIs { expectedSecondState }
                assertStateIs { expectedFinalState }

                assertIs<HomeViewAction.Error>(action)
                assertEquals(expectedMessage, (action as? HomeViewAction.Error)?.message)
            }
            verify { getFilterUseCase(any()) }
            verify { getCharactersUseCase(any()) }
        }
    }

    @Test
    fun `onPagination Should fetch next page When shouldFetchNextPage is true`() {
        // Given
        val characters = expectedSuccessDataResponse?.characters.toCharacters()
        runTest { subject = createSubjectWithInitSuccess(characters) }

        // When
        subject.onPagination(shouldFetchNextPage = true)

        // Then
        verify(exactly = 2) { getCharactersUseCase(any()) }
    }

    @Test
    fun `onPagination Should not fetch next page When shouldFetchNextPage is false`() {
        // Given
        val characters = expectedSuccessDataResponse?.characters.toCharacters()
        runTest { subject = createSubjectWithInitSuccess(characters) }

        // When
        subject.onPagination(shouldFetchNextPage = false)

        // Then
        verify { getCharactersUseCase(any()) }
    }

    @Test
    fun `onFabVisibility Should update shouldFetchNextPage state When invoke onPagination`() {
        // Given
        val expectedState = initialState.copy(shouldShowFabButton = false)
        subject = createSubject()

        // When
        subject.onFabVisibility(shouldShowFabButton = false)

        // Then
        runTest {
            subject.testStateFlow(backgroundScope) {
                assertStateIs { expectedState }
            }
        }
    }

    @Test
    fun `onShowDetails Should expose Details action When invoke method to show details`() {
        // Given
        val characters = expectedSuccessDataResponse?.characters.toCharacters().characters
        val expectedCharacter = characters.first()
        subject = createSubject()

        // When
        subject.onDetails(expectedCharacter)

        // Then
        runTest {
            subject.testActionFlow { action ->
                assertIs<HomeViewAction.Details>(action)
                assertEquals(expectedCharacter, (action as? HomeViewAction.Details)?.character)
            }
        }
    }

    @Test
    fun `onCloseError Should expose Filter action When invoke method to close error dialog`() {
        // Given
        val cause = FilterThrowable()
        subject = createSubject()

        // When
        subject.onCloseError(cause)

        // Then
        runTest {
            subject.testActionFlow { action ->
                assertIs<HomeViewAction.Filter>(action)
            }
        }
    }

    private fun createSubjectWithInitFailure(cause: Throwable): HomeViewModel {
        every { getFilterUseCase(any()) } returns flowOf(null)
        every { getCharactersUseCase(any()) } returns flow { throw cause }
        return createSubject()
    }

    private fun createSubjectWithInitSuccess(characters: Characters): HomeViewModel {
        every { getFilterUseCase(any()) } returns flowOf(null)
        every { getCharactersUseCase(any()) } returns flowOf(characters)
        return createSubject()
    }

    private fun createSubject(): HomeViewModel = HomeViewModel(
        getFilterUseCase = getFilterUseCase,
        getCharactersUseCase = getCharactersUseCase,
        dispatcher = dispatcherRule.testDispatcher
    )

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
