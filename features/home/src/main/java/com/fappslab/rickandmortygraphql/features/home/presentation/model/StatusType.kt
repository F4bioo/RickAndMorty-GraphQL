package com.fappslab.rickandmortygraphql.features.home.presentation.model

import androidx.annotation.DrawableRes
import com.fappslab.rickandmortygraphql.libraries.design.R

internal enum class StatusType(@DrawableRes val iconRes: Int) {
    Alive(iconRes = R.drawable.ds_ic_circle_green),
    Dead(iconRes = R.drawable.ds_ic_circle_red),
    Unknown(iconRes = R.drawable.ds_ic_circle_dark)
}
