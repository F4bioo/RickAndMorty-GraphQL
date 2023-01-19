package com.fappslab.rickandmortygraphql.features.home.presentation.viewmodel

import androidx.annotation.StringRes
import com.fappslab.rickandmortygraphql.core.common.domain.model.Character

internal sealed class HomeViewAction {
    object Filter : HomeViewAction()
    data class Details(val character: Character) : HomeViewAction()
    data class Error(@StringRes val message: Int, val cause: Throwable) : HomeViewAction()
}
