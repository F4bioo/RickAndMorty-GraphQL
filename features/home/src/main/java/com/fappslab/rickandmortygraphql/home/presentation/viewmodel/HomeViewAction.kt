package com.fappslab.rickandmortygraphql.home.presentation.viewmodel

internal sealed class HomeViewAction {
    data class Error(val message: String?) : HomeViewAction()
    object Refresh : HomeViewAction()
}
