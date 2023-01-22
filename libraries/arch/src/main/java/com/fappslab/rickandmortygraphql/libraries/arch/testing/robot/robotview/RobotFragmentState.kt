package com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.robotview

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.RobotCheck

interface RobotFragmentState<V : Fragment, CB : RobotCheck<CB>, S, VM : ViewModel> {
    fun givenState(state: () -> S) = this
    fun whenLaunch(fragment: (V) -> Unit = {}): CB
}
