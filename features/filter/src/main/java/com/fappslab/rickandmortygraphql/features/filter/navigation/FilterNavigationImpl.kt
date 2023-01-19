package com.fappslab.rickandmortygraphql.features.filter.navigation

import androidx.fragment.app.Fragment
import com.fappslab.rickandmortygraphql.features.filter.presentation.FilterFragment
import com.fappslab.rickandmortygraphql.core.common.navigation.FilterNavigation

internal class FilterNavigationImpl : FilterNavigation {

    override fun create(): Fragment =
        FilterFragment.create()
}
