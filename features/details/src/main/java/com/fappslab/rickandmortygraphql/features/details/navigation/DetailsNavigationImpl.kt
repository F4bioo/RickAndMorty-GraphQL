package com.fappslab.rickandmortygraphql.features.details.navigation

import androidx.fragment.app.Fragment
import com.fappslab.rickandmortygraphql.features.details.presentation.DetailsFragment
import com.fappslab.rickandmortygraphql.core.common.navigation.CharacterArgs
import com.fappslab.rickandmortygraphql.core.common.navigation.DetailsNavigation

internal class DetailsNavigationImpl : DetailsNavigation {

    override fun create(args: CharacterArgs): Fragment =
        DetailsFragment.createFragment(args)
}
