package com.fappslab.rickandmortygraphql.home.presentation.viewmodel

import com.fappslab.rickandmortygraphql.arch.rules.DispatcherTestRule
import com.fappslab.rickandmortygraphql.arch.test.testActionFlow
import com.fappslab.rickandmortygraphql.arch.test.testFlows
import com.fappslab.rickandmortygraphql.arch.test.testStateFlow
import com.fappslab.rickandmortygraphql.domain.usecase.GetCharactersUseCase
import com.fappslab.rickandmortygraphql.hubsrc.utils.StubResponse.expectedSuccessDataResponse
import com.fappslab.rickandmortygraphql.hubsrc.utils.toCharacters
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
            characters = characters.characters.toSet(),
            totalPages = 42,
            nextPage = 2
        )
        val expectedFinalState = expectedSecondState.copy(shouldShowLoading = false)

        // When
        subject = setupSubject(withInitBlockSuccess = true)

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
    fun `initBlockFailure Should expose Error When init block get failure`() {
        // Given
        val expectedMessage = "Error message."
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedFinalState = expectedFirstState.copy(shouldShowLoading = false)

        // When
        subject = setupSubject(withInitBlockSuccess = false)

        // Then
        runTest {
            verify { getCharactersUseCase(any()) }
            subject.testFlows(backgroundScope) { action ->
                assertStateIs { initialState }
                assertStateIs { expectedFirstState }
                assertStateIs { expectedFinalState }

                assertIs<HomeViewAction.Error>(action)
                assertEquals(expectedMessage, (action as? HomeViewAction.Error)?.message)
            }
        }
    }

    @Test
    fun `onPagination Should fetch next page When shouldFetchNextPage is true`() {
        // Given
        runTest { subject = setupSubject(withInitBlockSuccess = true) }

        // When
        subject.onPagination(shouldFetchNextPage = true)

        // Then
        verify(exactly = 2) { getCharactersUseCase(any()) }
    }

    @Test
    fun `onPagination Should not fetch next page When shouldFetchNextPage is false`() {
        // Given
        runTest { subject = setupSubject(withInitBlockSuccess = true) }

        // When
        subject.onPagination(shouldFetchNextPage = false)

        // Then
        verify { getCharactersUseCase(any()) }
    }

    @Test
    fun `onShowDetails Should update state When invoke method to show details`() {
        // Given
        val character = expectedSuccessDataResponse?.characters.toCharacters().characters.first()
        val expectedState = initialState.copy(shouldShowDetails = true, character = character)
        subject = setupSubject(withInitBlockSuccess = false)

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
        subject = setupSubject(withInitBlockSuccess = false)

        // When
        subject.onCloseDetails()

        // Then
        runTest {
            subject.testStateFlow(backgroundScope) {
                assertStateIs { expectedState }
            }
        }
    }

    @Test
    fun `onRefresh Should expose Refresh action When invoke method refresh`() {
        // Given
        subject = setupSubject(withInitBlockSuccess = false)

        // When
        subject.onRefresh()

        // Then
        runTest {
            subject.testActionFlow { action ->
                assertIs<HomeViewAction.Refresh>(action)
            }
        }
    }

    private fun setupSubject(withInitBlockSuccess: Boolean): HomeViewModel {
        val mockedFlowResult = if (withInitBlockSuccess) {
            flowOf(expectedSuccessDataResponse?.characters.toCharacters())
        } else flow { throw Throwable("Error message.") }
        every { getCharactersUseCase(any()) } returns mockedFlowResult

        return HomeViewModel(
            getCharactersUseCase = getCharactersUseCase,
            dispatcher = dispatcherRule.testDispatcher
        )
    }
}
