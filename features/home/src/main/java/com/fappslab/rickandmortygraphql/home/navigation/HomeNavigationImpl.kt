package com.fappslab.rickandmortygraphql.home.navigation

import androidx.fragment.app.Fragment
import com.fappslab.rickandmortygraphql.home.presentation.HomeFragment
import com.fappslab.rickandmortygraphql.navigation.HomeNavigation

internal class HomeNavigationImpl : HomeNavigation {

    override fun create(): Fragment =
        HomeFragment.create()
}
