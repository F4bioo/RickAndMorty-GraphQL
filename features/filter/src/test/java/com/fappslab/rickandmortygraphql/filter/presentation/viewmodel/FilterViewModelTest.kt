package com.fappslab.rickandmortygraphql.filter.presentation.viewmodel

import app.cash.turbine.test
import com.fappslab.rickandmortygraphql.libraries.arch.rules.DispatcherTestRule
import com.fappslab.rickandmortygraphql.domain.model.FilterType
import com.fappslab.rickandmortygraphql.domain.model.KeyType
import com.fappslab.rickandmortygraphql.domain.usecase.GetFilterUseCase
import com.fappslab.rickandmortygraphql.filter.R
import com.fappslab.rickandmortygraphql.filter.domain.usecase.DeleteFilterUseCase
import com.fappslab.rickandmortygraphql.filter.domain.usecase.SetFilterUseCase
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class FilterViewModelTest {

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private val initialState = FilterViewState()

    private val getFilterUseCase: GetFilterUseCase = mockk()
    private val setFilterUseCase: SetFilterUseCase = mockk()
    private val deleteFilterUseCase: DeleteFilterUseCase = mockk()
    private lateinit var subject: FilterViewModel

    @Before
    fun setUp() {
        subject = FilterViewModel(
            getFilterUseCase = getFilterUseCase,
            setFilterUseCase = setFilterUseCase,
            deleteFilterUseCase = deleteFilterUseCase
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getStatusFilter Should update idRadioStatus state When invoke getFilters`() {
        // Given
        val filterName = FilterType.StatusAlive.value
        val expectedState = initialState.copy(idRadioStatus = R.id.radio_status_alive)
        every { getFilterUseCase(any()) } returns flowOf(filterName)

        // When
        runTest { subject.getFilters() }

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
            verify(exactly = 3) { getFilterUseCase(any()) }
        }
    }

    @Test
    fun `getGenderFilter Should update idRadioGender state When invoke getFilters`() {
        // Given
        val filterName = FilterType.GenderMale.value
        val expectedState = initialState.copy(idRadioGender = R.id.radio_gender_male)
        every { getFilterUseCase(any()) } returns flowOf(filterName)

        // When
        runTest { subject.getFilters() }

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
            verify(exactly = 3) { getFilterUseCase(any()) }
        }
    }

    @Test
    fun `getSpeciesFilter Should update idRadioSpecies state When invoke getFilters`() {
        // Given
        val filterName = FilterType.SpeciesHuman.value
        val expectedState = initialState.copy(idRadioSpecies = R.id.radio_species_human)
        every { getFilterUseCase(any()) } returns flowOf(filterName)

        // When
        runTest { subject.getFilters() }

        // Then
        runTest {
            subject.state.test {
                assertEquals(expectedState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
            verify(exactly = 3) { getFilterUseCase(any()) }
        }
    }

    @Test
    fun `saveFilter Should save filter locally When invoke radioFilterBehavior`() {
        // Given
        every { setFilterUseCase(any(), any()) } returns flowOf(Unit)

        // When
        subject.radioFilterBehavior(KeyType.KeyStatus, R.id.radio_status_alive)

        // Then
        verify { setFilterUseCase(any(), any()) }
    }

    @Test
    fun `saveFilter Should delete filter locally When invoke radioFilterBehavior`() {
        // Given
        every { deleteFilterUseCase(any()) } returns flowOf(Unit)

        // When
        subject.radioFilterBehavior(KeyType.KeyStatus, 1)

        // Then
        verify { deleteFilterUseCase(any()) }
    }
}
