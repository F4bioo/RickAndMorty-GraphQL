package com.fappslab.rickandmortygraphql.libraries.arch.testing.robot

import android.app.Activity
import androidx.fragment.app.Fragment
import com.fappslab.rickandmortygraphql.libraries.arch.viewmodel.ViewModel

interface RobotActivity<V : Activity, CB : RobotCheck<CB>, S, A, VM : ViewModel<S, A>> {
    fun givenState(state: () -> S): RobotActivity<V, CB, S, A, VM> = this
    fun givenAction(invoke: VM.() -> Unit, action: () -> A): RobotActivity<V, CB, S, A, VM> = this
    fun whenLaunch(activity: (V) -> Unit = {}): CB
}

interface RobotFragment<V : Fragment, CB : RobotCheck<CB>, S, A, VM : ViewModel<S, A>> {
    fun givenState(state: () -> S): RobotFragment<V, CB, S, A, VM> = this
    fun givenAction(invoke: VM.() -> Unit, action: () -> A): RobotFragment<V, CB, S, A, VM> = this
    fun whenLaunch(fragment: (V) -> Unit = {}): CB
}
