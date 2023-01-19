package com.fappslab.rickandmortygraphql.core.common.domain.stub

import com.fappslab.rickandmortygraphql.core.common.domain.model.Characters

fun characterStub() = Characters(
    characters = emptyList(),
    nextPage = 2, totalPages = 42
)
