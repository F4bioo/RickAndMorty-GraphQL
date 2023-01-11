package com.fappslab.rickandmortygraphql.navigation

import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlinx.parcelize.Parcelize

interface DetailsNavigation {
    fun navigationToFeature(args: CharacterArgs): Fragment
}

@Parcelize
data class CharacterArgs(
    val id: String,
    val name: String,
    val image: String,
    val status: String,
    val species: String,
    val gender: String,
    val originName: String
) : Parcelable
