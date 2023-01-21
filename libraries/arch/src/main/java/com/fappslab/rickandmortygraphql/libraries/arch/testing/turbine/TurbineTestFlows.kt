package com.fappslab.rickandmortygraphql.libraries.arch.testing.turbine

import androidx.annotation.VisibleForTesting
import app.cash.turbine.test
import app.cash.turbine.testIn
import com.fappslab.rickandmortygraphql.libraries.arch.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import java.lang.reflect.Modifier
import kotlin.test.assertEquals

/**
 * @author [Fabio Marinho]
 * @see <a href="https://github.com/F4bioo">GitHub</a>
 *
 * <P>Test state (StateFlow<T>) flow and action flow (SharedFlow<T>) from ViewModel.
 * This function allows you to specify a block of code
 * that will be executed with the state and action flows as arguments, and will assert that the state
 * flow produces the expected values.</p>
 *
 * @param backgroundScope the CoroutineScope in which the test will be run.
 * @param flowsBlock a block of code that receives the state and action flows as arguments and can
 *     perform assertions on them.
 */
@VisibleForTesting(otherwise = Modifier.PRIVATE)
suspend fun <S, A> ViewModel<S, A>.testFlows(
    backgroundScope: CoroutineScope,
    flowsBlock: suspend StateEvents<S>.(A) -> Unit
) {
    val stateTurbine = state.testIn(backgroundScope)
    val actionTurbine = action.testIn(backgroundScope)
    val events = StateEvents<S>()

    events.states.forEach { state ->
        assertEquals(state, stateTurbine.awaitItem())
    }

    flowsBlock(events, actionTurbine.awaitItem())

    stateTurbine.cancelAndConsumeRemainingEvents()
    actionTurbine.cancelAndConsumeRemainingEvents()
}

/**
 * @author [Fabio Marinho]
 * @see <a href="https://github.com/F4bioo">GitHub</a>
 *
 * <P>Test the state flow (StateFlow<T>) from ViewModel. This function allows you to specify a block of code that will
 * be executed with the state flow as an argument, and will assert that the state flow produces the
 * expected values.</p>
 *
 * @param backgroundScope the CoroutineScope in which the test will be run.
 * @param stateFlowBlock a block of code that receives the state flow as an argument and can perform
 *     assertions on it.
 */
@VisibleForTesting(otherwise = Modifier.PRIVATE)
suspend fun <S, A> ViewModel<S, A>.testStateFlow(
    backgroundScope: CoroutineScope,
    stateFlowBlock: suspend StateEvents<S>.() -> Unit
) {
    val events = StateEvents<S>().apply { stateFlowBlock() }
    val stateTurbine = state.testIn(backgroundScope)

    events.states.forEach { state ->
        assertEquals(state, stateTurbine.awaitItem())
    }
    stateTurbine.cancelAndConsumeRemainingEvents()
}

/**
 * @author [Fabio Marinho]
 * @see <a href="https://github.com/F4bioo">GitHub</a>
 *
 * <P>Test the action flow (SharedFlow<T>) from ViewModel. This function allows you to specify a block of code that will
 * be executed with the action flow as an argument, and will assert that the action flow produces the
 * expected values.</p>
 *
 * @param actionFlowBlock a block of code that receives the action flow as an argument and can perform
 *     assertions on it.
 */
@VisibleForTesting(otherwise = Modifier.PRIVATE)
suspend fun <S, A> ViewModel<S, A>.testActionFlow(
    actionFlowBlock: suspend (A) -> Unit
) {
    action.test {
        actionFlowBlock(awaitItem())
        cancelAndConsumeRemainingEvents()
    }
}

/**
 * @author [Fabio Marinho]
 * @see <a href="https://github.com/F4bioo">GitHub</a>
 *
 * <P>This class is for internal use only and should not be used outside of the library.</p>
 */
@VisibleForTesting(otherwise = Modifier.PRIVATE)
class StateEvents<S>(val states: MutableList<S> = mutableListOf()) {
    fun assertStateIs(state: () -> S) {
        states.add(state())
    }
}
