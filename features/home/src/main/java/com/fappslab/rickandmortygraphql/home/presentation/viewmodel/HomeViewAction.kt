package com.fappslab.rickandmortygraphql.home.presentation.viewmodel

import androidx.annotation.StringRes

internal sealed class HomeViewAction {
    data class Error(@StringRes val message: Int) : HomeViewAction()
}
