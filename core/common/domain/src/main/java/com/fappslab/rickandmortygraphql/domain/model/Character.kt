package com.fappslab.rickandmortygraphql.domain.model

data class Character(
    val id: String,
    val name: String,
    val image: String,
    val status: String,
    val species: String,
    val gender: String,
    val episode: List<Episode>,
    val location: Location,
    val origin: Origin,
    val info: Info
) {

    data class Episode(
        val id: String,
        val name: String,
        val airDate: String
    )

    data class Location(
        val id: String,
        val name: String,
        val type: String,
        val dimension: String
    )

    data class Origin(
        val id: String,
        val name: String,
        val type: String,
        val dimension: String
    )

    data class Info(
        val pages: Int,
        val count: Int,
        val next: Int,
    )
}
