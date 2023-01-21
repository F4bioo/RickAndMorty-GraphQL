package com.fappslab.rickandmortygraphql.features.details.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.rickandmortygraphql.core.common.navigation.CharacterArgs
import com.fappslab.rickandmortygraphql.features.details.presentation.DetailsFragment
import com.fappslab.rickandmortygraphql.features.details.presentation.viewmodel.DetailsViewModel
import com.fappslab.rickandmortygraphql.features.details.presentation.viewmodel.DetailsViewState
import com.fappslab.rickandmortygraphql.features.details.stub.characterArgsStub
import com.fappslab.rickandmortygraphql.libraries.design.R
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTestRule
import kotlin.test.assertIs

internal const val KEY_ARGS = "arch:key_args"

@RunWith(AndroidJUnit4::class)
internal class DetailsNavigationImplTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create { modules(setupModules()) }

    private val subject = DetailsNavigationImpl()

    private fun setupModules() = module {
        viewModel { (args: CharacterArgs) ->
            mockk<DetailsViewModel>(relaxed = true) {
                every { state } returns MutableStateFlow(DetailsViewState(args))
            }
        }
    }

    @Test
    fun whenInvokeCreate_shouldOpenDetailsFragment() {
        // Given
        val args = characterArgsStub()
        val bundle = bundleOf(KEY_ARGS to args)
        val expectedFragmentName = "DetailsFragment"

        // When
        val scenario = launchFragment(themeResId = R.style.Theme_Ds, fragmentArgs = bundle) {
            subject.create(args)
        }

        // Then
        scenario.onFragment { fragment ->
            assertIs<DetailsFragment>(fragment)
            assertEquals(expectedFragmentName, fragment.javaClass.simpleName)
        }
    }
}
