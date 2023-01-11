package com.fappslab.rickandmortygraphql.home.presentation.model.extension

import com.fappslab.rickandmortygraphql.domain.model.Character
import com.fappslab.rickandmortygraphql.navigation.CharacterArgs

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
