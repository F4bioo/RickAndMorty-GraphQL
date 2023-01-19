package com.fappslab.rickandmortygraphql.core.data.hubsrc.extension

import com.apollographql.apollo3.api.Optional
import com.fappslab.rickandmortygraphql.libraries.arch.extension.orZero
import com.fappslab.rickandmortygraphql.core.common.domain.model.Character
import com.fappslab.rickandmortygraphql.core.common.domain.model.Characters
import com.fappslab.rickandmortygraphql.core.common.domain.model.Filter
import com.fappslab.rickandmortygraphql.remote.GetCharactersFilterQuery
import com.fappslab.rickandmortygraphql.remote.type.FilterCharacter

fun List<GetCharactersFilterQuery.Episode?>?.toEpisodes() =
    this?.map { it.toEpisode() }.orEmpty()

fun List<GetCharactersFilterQuery.Result?>?.toCharacters() =
    this?.map { it.toCharacter() }.orEmpty()

fun GetCharactersFilterQuery.Characters?.toCharacters() =
    Characters(
        characters = this?.results.toCharacters(),
        totalPages = this?.info?.pages.orZero(),
        nextPage = this?.info?.next.orZero()
    )

fun GetCharactersFilterQuery.Result?.toCharacter() =
    Character(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        image = this?.image.orEmpty(),
        status = this?.status.orEmpty(),
        species = this?.species.orEmpty(),
        gender = this?.gender.orEmpty(),
        episode = this?.episode.toEpisodes(),
        origin = this?.origin.toOrigin()
    )

fun GetCharactersFilterQuery.Episode?.toEpisode() =
    Character.Episode(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        airDate = this?.air_date.orEmpty(),
    )

fun GetCharactersFilterQuery.Origin?.toOrigin() =
    Character.Origin(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        dimension = this?.dimension.orEmpty()
    )

fun Filter.toFilterCharacter() =
    FilterCharacter(
        name = Optional.present(name),
        status = Optional.present(status),
        species = Optional.present(species),
        gender = Optional.present(gender)
    )
