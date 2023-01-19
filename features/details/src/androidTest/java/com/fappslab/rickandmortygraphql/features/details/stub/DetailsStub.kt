package com.fappslab.rickandmortygraphql.features.details.stub

import com.fappslab.rickandmortygraphql.core.common.navigation.CharacterArgs

internal fun characterArgsStub() =
    CharacterArgs(
        id = "1",
        name = "Rick Sanchez",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        status = "Alive",
        species = "Human",
        gender = "Male",
        originName = "Earth (C-137)"
    )
