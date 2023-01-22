package com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.robotview

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.RobotCheck

interface RobotFragment<V : Fragment, CB : RobotCheck<CB>, S, A, VM : ViewModel> {
    fun givenState(state: () -> S) = this
    fun givenAction(invoke: VM.() -> Unit, action: () -> A) = this
    fun whenLaunch(fragment: (V) -> Unit = {}): CB
}
