package com.fappslab.rickandmortygraphql.features.home.presentation.viewmodel

import com.fappslab.rickandmortygraphql.core.common.domain.model.Characters
import com.fappslab.rickandmortygraphql.core.common.domain.model.Filter
import com.fappslab.rickandmortygraphql.core.common.domain.usecase.GetCharactersUseCase
import com.fappslab.rickandmortygraphql.core.common.domain.usecase.GetFilterUseCase
import com.fappslab.rickandmortygraphql.core.data.hubsrc.utils.StubResponse.expectedSuccessDataResponse
import com.fappslab.rickandmortygraphql.core.data.hubsrc.utils.toCharacters
import com.fappslab.rickandmortygraphql.core.data.remote.client.network.exception.RemoteThrowable.ClientThrowable
import com.fappslab.rickandmortygraphql.core.data.remote.client.network.exception.RemoteThrowable.ServerThrowable
import com.fappslab.rickandmortygraphql.core.data.remote.client.network.exception.RemoteThrowable.UnknownThrowable
import com.fappslab.rickandmortygraphql.libraries.arch.testing.rules.DispatcherTestRule
import com.fappslab.rickandmortygraphql.libraries.arch.testing.turbine.testStateFlow
import com.fappslab.rickandmortygraphql.libraries.design.R
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
    fun `initBlockSuccess Should update state When init block get success with empty list`() {
        // Given
        val characters = Characters(characters = emptyList(), totalPages = 0, nextPage = 0)
        val expectedFirstState = initialState.copy(
            shouldShowLoading = true,
            shouldShowTryAgain = false
        )
        val expectedSecondState = expectedFirstState.copy(
            shouldShowEmptyLayout = true,
            shouldShowTryAgain = true,
            shouldShowError = true,
            errorMessageRes = R.string.common_filter_error
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
    fun `initBlockFailure Should Should update state When init block get ClientThrowable`() {
        // Given
        val (expectedFirstState, expectedSecondState, expectedFinalState) =
            setupFailureStates(errorMessageRes = R.string.common_client_error)

        // When
        subject = createSubjectWithInitFailure(ClientThrowable())

        // Then
        runTest {
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
    fun `initBlockFailure Should update state When init block get ServerThrowable`() {
        // Given
        val (expectedFirstState, expectedSecondState, expectedFinalState) =
            setupFailureStates(errorMessageRes = R.string.common_server_error)

        // When
        subject = createSubjectWithInitFailure(ServerThrowable())

        // Then
        runTest {
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
    fun `initBlockFailure Should update state When init block get UnknownThrowable`() {
        // Given
        val (expectedFirstState, expectedSecondState, expectedFinalState) =
            setupFailureStates(errorMessageRes = R.string.common_unknown_error)

        // When
        subject = createSubjectWithInitFailure(UnknownThrowable())

        // Then
        runTest {
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
    fun `onShowDetails Should update state When invoke method to show details`() {
        // Given
        val characters = expectedSuccessDataResponse?.characters.toCharacters().characters
        val expectedCharacter = characters.first()
        val expectedState = initialState.copy(
            shouldShowDetails = true,
            character = expectedCharacter
        )
        subject = createSubject()

        // When
        subject.onDetails(expectedCharacter)

        // Then
        runTest {
            subject.testStateFlow(backgroundScope) {
                assertStateIs { expectedState }
            }
        }
    }

    @Test
    fun `onCloseError Should update state When invoke method to close error dialog`() {
        // Given
        val expectedState = initialState.copy(shouldShowError = false)
        subject = createSubject()

        // When
        subject.onCloseError()

        // Then
        runTest {
            subject.testStateFlow(backgroundScope) {
                assertStateIs { expectedState }
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

    private fun setupFailureStates(
        errorMessageRes: Int
    ): Triple<HomeViewState, HomeViewState, HomeViewState> {
        val expectedFirstState = initialState.copy(
            shouldShowLoading = true,
            shouldShowTryAgain = false
        )
        val expectedSecondState = expectedFirstState.copy(
            shouldShowLoading = false
        )
        val expectedFinalState = initialState.copy(
            shouldShowEmptyLayout = true,
            shouldShowTryAgain = true,
            shouldShowError = true,
            errorMessageRes = errorMessageRes
        )
        return Triple(expectedFirstState, expectedSecondState, expectedFinalState)
    }
}
