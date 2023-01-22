package com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.robotview

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.RobotCheck

interface RobotActivityState<V : Activity, CB : RobotCheck<CB>, S, VM : ViewModel> {
    fun givenState(state: () -> S) = this
    fun whenLaunch(fragment: (V) -> Unit = {}): CB
}
