package com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.robotview

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.RobotCheck

interface RobotActivityAction<V : Activity, CB : RobotCheck<CB>, A, VM : ViewModel> {
    fun givenAction(invoke: VM.() -> Unit, action: () -> A) = this
    fun whenLaunch(fragment: (V) -> Unit = {}): CB
}
