package com.fappslab.rickandmortygraphql.domain.model

data class Filter(
    val name: String? = null,
    val status: String? = null,
    val gender: String? = null,
    val species: String? = null,
    val page: Int = 1
)
