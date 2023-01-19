package com.fappslab.rickandmortygraphql.features.home.navigation

import androidx.fragment.app.Fragment
import com.fappslab.rickandmortygraphql.features.home.presentation.HomeFragment
import com.fappslab.rickandmortygraphql.navigation.HomeNavigation

internal class HomeNavigationImpl : HomeNavigation {

    override fun create(): Fragment =
        HomeFragment.create()
}
