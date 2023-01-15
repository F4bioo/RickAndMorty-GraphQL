package com.fappslab.rickandmortygraphql.filter.navigation

import androidx.fragment.app.Fragment
import com.fappslab.rickandmortygraphql.filter.presentation.FilterFragment
import com.fappslab.rickandmortygraphql.navigation.FilterNavigation

internal class FilterNavigationImpl : FilterNavigation {

    override fun create(): Fragment =
        FilterFragment.create()
}
