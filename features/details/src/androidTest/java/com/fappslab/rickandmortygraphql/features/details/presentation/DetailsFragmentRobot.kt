package com.fappslab.rickandmortygraphql.features.details.presentation

import androidx.core.os.bundleOf
import com.fappslab.rickandmortygraphql.core.common.navigation.CharacterArgs
import com.fappslab.rickandmortygraphql.features.details.presentation.viewmodel.DetailsViewModel
import com.fappslab.rickandmortygraphql.features.details.presentation.viewmodel.DetailsViewState
import com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.robotview.RobotFragmentState
import com.fappslab.rickandmortygraphql.libraries.arch.testing.rules.FragmentTestRule
import com.fappslab.rickandmortygraphql.libraries.design.R
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val KEY_ARGS = "arch:key_args"

private typealias DetailsRobotFragmentSingleAlias =
        RobotFragmentState<DetailsFragment, DetailsFragmentRobotCheck,
                DetailsViewState, DetailsViewModel>

internal class DetailsFragmentRobot(
    val args: CharacterArgs
) : FragmentTestRule<DetailsFragment>(
    fragmentKClass = DetailsFragment::class,
    fragmentArgs = bundleOf(KEY_ARGS to args),
    themeResId = R.style.Theme_Ds
), DetailsRobotFragmentSingleAlias {

    private val fakeViewModel = mockk<DetailsViewModel>(relaxed = true) {
        every { state } returns MutableStateFlow(DetailsViewState(args))
    }

    override fun setupModules() = module {
        viewModel { fakeViewModel }
    }

    override fun whenLaunch(
        fragment: (DetailsFragment) -> Unit
    ): DetailsFragmentRobotCheck {
        launchFragment { fragment(it) }
        return DetailsFragmentRobotCheck()
    }
}
