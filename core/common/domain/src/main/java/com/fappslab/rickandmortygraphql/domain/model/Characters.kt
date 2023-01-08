package com.fappslab.rickandmortygraphql.domain.model

data class Characters(
    val characters: List<Character>,
    val totalPages: Int,
    val nextPage: Int
)
