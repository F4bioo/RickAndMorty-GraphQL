package com.fappslab.rickandmortygraphql.hubsrc.extension

import com.fappslab.rickandmortygraphql.domain.model.Character
import com.fappslab.rickandmortygraphql.remote.GetCharactersQuery

fun List<GetCharactersQuery.Episode?>?.toEpisodes() =
    this?.map { it.toEpisode() }.orEmpty()

fun GetCharactersQuery.Info?.toInfo() =
    Character.Info(
        pages = this?.pages ?: 0,
        count = this?.pages ?: 0,
        next = this?.pages ?: 0
    )

fun GetCharactersQuery.Characters?.toCharacters() =
    this?.results?.map { it.toCharacter(info.toInfo()) }.orEmpty()

fun GetCharactersQuery.Result?.toCharacter(info: Character.Info) =
    Character(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        image = this?.image.orEmpty(),
        status = this?.status.orEmpty(),
        species = this?.species.orEmpty(),
        gender = this?.gender.orEmpty(),
        episode = this?.episode.toEpisodes(),
        location = this?.location.toLocation(),
        origin = this?.origin.toOrigin(),
        info = info
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
