package com.fappslab.rickandmortygraphql.home.navigation

import androidx.fragment.app.testing.launchFragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.rickandmortygraphql.design.R
import com.fappslab.rickandmortygraphql.home.presentation.HomeFragment
import com.fappslab.rickandmortygraphql.home.presentation.viewmodel.HomeViewModel
import com.fappslab.rickandmortygraphql.home.presentation.viewmodel.HomeViewState
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
internal class HomeNavigationImplTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create { modules(setupModules()) }

    private fun setupModules() = module {
        viewModel {
            mockk<HomeViewModel>(relaxed = true) {
                every { state } returns MutableStateFlow(HomeViewState())
                every { action } returns MutableSharedFlow()
            }
        }
    }

    @Test
    fun whenInvokeNavigationToFeature_shouldOpenHomeFragment() {
        // Given
        val subject = HomeNavigationImpl()
        val expectedFragmentName = "HomeFragment"

        // When
        val scenario = launchFragment(themeResId = R.style.Theme_Ds) {
            subject.navigationToFeature()
        }

        // Then
        scenario.onFragment { fragment ->
            assertIs<HomeFragment>(fragment)
            assertEquals(expectedFragmentName, fragment.javaClass.simpleName)
        }
    }
}
