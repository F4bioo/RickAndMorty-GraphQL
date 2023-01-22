package com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.robotview

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.RobotCheck

interface RobotActivity<V : Activity, CB : RobotCheck<CB>, S, A, VM : ViewModel> {
    fun givenState(state: () -> S) = this
    fun givenAction(invoke: VM.() -> Unit, action: () -> A) = this
    fun whenLaunch(activity: (V) -> Unit = {}): CB
}
