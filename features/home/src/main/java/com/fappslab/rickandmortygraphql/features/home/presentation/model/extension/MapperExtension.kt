package com.fappslab.rickandmortygraphql.features.home.presentation.model.extension

import com.fappslab.rickandmortygraphql.core.common.domain.model.Character
import com.fappslab.rickandmortygraphql.core.common.navigation.CharacterArgs

internal fun Character.toCharacterArgs() =
    CharacterArgs(
        id = id,
        name = name,
        image = image,
        status = status,
        species = species,
        gender = gender,
        originName = origin.name
    )
