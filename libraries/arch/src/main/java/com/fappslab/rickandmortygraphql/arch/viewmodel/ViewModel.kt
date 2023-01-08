package com.fappslab.rickandmortygraphql.arch.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel as AndroidxViewModel

abstract class ViewModel<S, A>(
    initialState: S
) : AndroidxViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _action = MutableSharedFlow<A>()
    val action = _action.asSharedFlow()

    @Suppress("unused")
    protected fun AndroidxViewModel.onState(stateBlock: (S) -> S) {
        _state.update { stateBlock(it) }
    }

    protected fun AndroidxViewModel.onAction(actionBlock: () -> A) {
        viewModelScope.launch { _action.emit(actionBlock()) }
    }
}
