package com.fappslab.rickandmortygraphql.hubsrc.utils

import com.fappslab.rickandmortygraphql.arch.extension.orZero
import com.fappslab.rickandmortygraphql.domain.model.Character
import com.fappslab.rickandmortygraphql.domain.model.Characters
import com.fappslab.rickandmortygraphql.hubsrc.utils.QueryResponse.DataResponse.CharactersResponse
import com.fappslab.rickandmortygraphql.hubsrc.utils.QueryResponse.DataResponse.CharactersResponse.ResultResponse
import com.fappslab.rickandmortygraphql.hubsrc.utils.QueryResponse.DataResponse.CharactersResponse.ResultResponse.EpisodeResponse
import com.fappslab.rickandmortygraphql.hubsrc.utils.QueryResponse.DataResponse.CharactersResponse.ResultResponse.LocationResponse
import com.fappslab.rickandmortygraphql.hubsrc.utils.QueryResponse.DataResponse.CharactersResponse.ResultResponse.OriginResponse
import com.google.gson.annotations.SerializedName

data class QueryResponse(
    @SerializedName("data")
    val data: DataResponse?
) {

    data class DataResponse(
        @SerializedName("characters")
        val characters: CharactersResponse?
    ) {

        data class CharactersResponse(
            @SerializedName("results")
            val results: List<ResultResponse?>?,
            @SerializedName("info")
            val info: InfoResponse?
        ) {

            data class ResultResponse(
                @SerializedName("id")
                val id: String?,
                @SerializedName("name")
                val name: String?,
                @SerializedName("image")
                val image: String?,
                @SerializedName("status")
                val status: String?,
                @SerializedName("species")
                val species: String?,
                @SerializedName("gender")
                val gender: String?,
                @SerializedName("episode")
                val episodes: List<EpisodeResponse?>?,
                @SerializedName("location")
                val location: LocationResponse?,
                @SerializedName("origin")
                val origin: OriginResponse?
            ) {

                data class EpisodeResponse(
                    @SerializedName("id")
                    val id: String?,
                    @SerializedName("name")
                    val name: String?,
                    @SerializedName("air_date")
                    val airDate: String?
                )

                data class LocationResponse(
                    @SerializedName("id")
                    val id: String?,
                    @SerializedName("name")
                    val name: String?,
                    @SerializedName("type")
                    val type: String?,
                    @SerializedName("dimension")
                    val dimension: String?
                )

                data class OriginResponse(
                    @SerializedName("id")
                    val id: String?,
                    @SerializedName("name")
                    val name: String?,
                    @SerializedName("type")
                    val type: String?,
                    @SerializedName("dimension")
                    val dimension: String?
                )
            }

            data class InfoResponse(
                @SerializedName("pages")
                val pages: Int?,
                @SerializedName("count")
                val count: Int?,
                @SerializedName("next")
                val next: Int?
            )
        }
    }
}

fun List<EpisodeResponse?>?.toEpisodes() =
    this?.map { it.toEpisode() }.orEmpty()

fun List<ResultResponse?>?.toCharacters() =
    this?.map { it.toCharacter() }.orEmpty()

fun CharactersResponse?.toCharacters() =
    Characters(
        characters = this?.results.toCharacters(),
        totalPages = this?.info?.pages.orZero(),
        nextPage = this?.info?.next.orZero()
    )

fun ResultResponse?.toCharacter() =
    Character(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        image = this?.image.orEmpty(),
        status = this?.status.orEmpty(),
        species = this?.species.orEmpty(),
        gender = this?.gender.orEmpty(),
        episode = this?.episodes.toEpisodes(),
        location = this?.location.toLocation(),
        origin = this?.origin.toOrigin()
    )

fun EpisodeResponse?.toEpisode() =
    Character.Episode(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        airDate = this?.airDate.orEmpty(),
    )

fun LocationResponse?.toLocation() =
    Character.Location(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        type = this?.type.orEmpty(),
        dimension = this?.dimension.orEmpty()
    )

fun OriginResponse?.toOrigin() =
    Character.Origin(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        type = this?.type.orEmpty(),
        dimension = this?.dimension.orEmpty()
    )
