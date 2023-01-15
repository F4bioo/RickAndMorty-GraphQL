package com.fappslab.rickandmortygraphql.domain.model

data class Character(
    val id: String,
    val name: String,
    val image: String,
    val status: String,
    val species: String,
    val gender: String,
    val episode: List<Episode>,
    val origin: Origin
) {

    data class Episode(
        val id: String,
        val name: String,
        val airDate: String
    )

    data class Origin(
        val id: String,
        val name: String,
        val dimension: String
    )
}
