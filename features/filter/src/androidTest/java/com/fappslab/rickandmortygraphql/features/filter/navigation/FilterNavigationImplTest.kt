package com.fappslab.rickandmortygraphql.features.filter.navigation

import androidx.fragment.app.testing.launchFragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.rickandmortygraphql.features.filter.presentation.FilterFragment
import com.fappslab.rickandmortygraphql.features.filter.presentation.viewmodel.FilterViewModel
import com.fappslab.rickandmortygraphql.features.filter.presentation.viewmodel.FilterViewState
import com.fappslab.rickandmortygraphql.libraries.design.R
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTestRule
import kotlin.test.assertEquals
import kotlin.test.assertIs

@RunWith(AndroidJUnit4::class)
internal class FilterNavigationImplTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create { modules(setupModules()) }

    private fun setupModules() = module {
        viewModel {
            mockk<FilterViewModel>(relaxed = true) {
                every { state } returns MutableStateFlow(FilterViewState())
                every { action } returns MutableSharedFlow()
            }
        }
    }

    @Test
    fun whenInvokeCreate_shouldOpenFilterFragment() {
        // Given
        val subject = FilterNavigationImpl()
        val expectedFragmentName = "FilterFragment"

        // When
        val scenario = launchFragment(themeResId = R.style.Theme_Ds) {
            subject.create()
        }

        // Then
        scenario.onFragment { fragment ->
            assertIs<FilterFragment>(fragment)
            assertEquals(expectedFragmentName, fragment.javaClass.simpleName)
        }
    }
}
