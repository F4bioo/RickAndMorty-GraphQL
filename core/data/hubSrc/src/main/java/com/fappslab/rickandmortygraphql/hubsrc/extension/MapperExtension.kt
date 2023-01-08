package com.fappslab.rickandmortygraphql.hubsrc.extension

import com.fappslab.rickandmortygraphql.arch.extension.orZero
import com.fappslab.rickandmortygraphql.domain.model.Character
import com.fappslab.rickandmortygraphql.domain.model.Characters
import com.fappslab.rickandmortygraphql.remote.GetCharactersQuery

fun List<GetCharactersQuery.Episode?>?.toEpisodes() =
    this?.map { it.toEpisode() }.orEmpty()

fun List<GetCharactersQuery.Result?>?.toCharacters() =
    this?.map { it.toCharacter() }.orEmpty()

fun GetCharactersQuery.Characters?.toCharacters() =
    Characters(
        characters = this?.results.toCharacters(),
        totalPages = this?.info?.pages.orZero(),
        nextPage = this?.info?.next.orZero()
    )

fun GetCharactersQuery.Result?.toCharacter() =
    Character(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        image = this?.image.orEmpty(),
        status = this?.status.orEmpty(),
        species = this?.species.orEmpty(),
        gender = this?.gender.orEmpty(),
        episode = this?.episode.toEpisodes(),
        location = this?.location.toLocation(),
        origin = this?.origin.toOrigin()
    )

fun GetCharactersQuery.Episode?.toEpisode() =
    Character.Episode(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        airDate = this?.air_date.orEmpty(),
    )

fun GetCharactersQuery.Location?.toLocation() =
    Character.Location(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        type = this?.type.orEmpty(),
        dimension = this?.dimension.orEmpty()
    )

fun GetCharactersQuery.Origin?.toOrigin() =
    Character.Origin(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        type = this?.type.orEmpty(),
        dimension = this?.dimension.orEmpty()
    )
