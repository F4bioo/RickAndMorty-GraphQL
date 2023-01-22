package com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.robotview

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.RobotCheck

interface RobotFragmentAction<V : Fragment, CB : RobotCheck<CB>, A, VM : ViewModel> {
    fun givenAction(invoke: VM.() -> Unit, action: () -> A) = this
    fun whenLaunch(fragment: (V) -> Unit = {}): CB
}
