package com.fappslab.rickandmortygraphql.details.presentation.model

import androidx.annotation.ColorRes
import com.fappslab.rickandmortygraphql.design.R

internal enum class StatusType(@ColorRes val colorRes: Int) {
    Alive(colorRes = R.color.ds_color_green),
    Dead(colorRes = R.color.ds_color_red),
    Unknown(colorRes = R.color.ds_color_dark_light)
}
