package com.fappslab.rickandmortygraphql.details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.fappslab.rickandmortygraphql.details.presentation.model.StatusType
import com.fappslab.rickandmortygraphql.navigation.CharacterArgs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class DetailsViewModel(
    args: CharacterArgs
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsViewState(character = args))
    val state = _state.asStateFlow()

    init {
        statusColorHandle(args.status)
    }

    private fun statusColorHandle(status: String) {
        val statusType = when (status) {
            StatusType.Alive.name -> StatusType.Alive
            StatusType.Dead.name -> StatusType.Dead
            else -> StatusType.Unknown
        }

        _state.update { it.copy(statusType = statusType) }
    }
}
