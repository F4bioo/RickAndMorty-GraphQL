package com.fappslab.rickandmortygraphql.features.details.presentation.viewmodel

import com.fappslab.rickandmortygraphql.features.details.presentation.model.StatusType
import com.fappslab.rickandmortygraphql.core.common.navigation.CharacterArgs

internal data class DetailsViewState(
    val character: CharacterArgs,
    val statusType: StatusType = StatusType.Alive
)
