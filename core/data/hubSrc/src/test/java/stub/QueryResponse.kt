package stub

import com.fappslab.rickandmortygraphql.domain.model.Character
import com.google.gson.annotations.SerializedName
import stub.QueryResponse.DataResponse.CharactersResponse
import stub.QueryResponse.DataResponse.CharactersResponse.InfoResponse
import stub.QueryResponse.DataResponse.CharactersResponse.ResultResponse
import stub.QueryResponse.DataResponse.CharactersResponse.ResultResponse.EpisodeResponse
import stub.QueryResponse.DataResponse.CharactersResponse.ResultResponse.LocationResponse
import stub.QueryResponse.DataResponse.CharactersResponse.ResultResponse.OriginResponse

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

fun InfoResponse?.toInfo() =
    Character.Info(
        pages = this?.pages ?: 0,
        count = this?.pages ?: 0,
        next = this?.pages ?: 0
    )

fun CharactersResponse?.toCharacters() =
    this?.results?.map { it.toCharacter(info.toInfo()) }.orEmpty()

fun ResultResponse?.toCharacter(info: Character.Info) =
    Character(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        image = this?.image.orEmpty(),
        status = this?.status.orEmpty(),
        species = this?.species.orEmpty(),
        gender = this?.gender.orEmpty(),
        episode = this?.episodes.toEpisodes(),
        location = this?.location.toLocation(),
        origin = this?.origin.toOrigin(),
        info = info
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