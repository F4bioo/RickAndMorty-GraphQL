package com.fappslab.rickandmortygraphql.details.presentation.viewmodel

import com.fappslab.rickandmortygraphql.details.presentation.model.StatusType
import com.fappslab.rickandmortygraphql.navigation.CharacterArgs

internal data class DetailsViewState(
    val character: CharacterArgs,
    val statusType: StatusType = StatusType.Alive
)
