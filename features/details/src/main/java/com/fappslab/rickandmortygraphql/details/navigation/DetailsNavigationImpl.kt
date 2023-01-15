package com.fappslab.rickandmortygraphql.details.navigation

import androidx.fragment.app.Fragment
import com.fappslab.rickandmortygraphql.details.presentation.DetailsFragment
import com.fappslab.rickandmortygraphql.navigation.CharacterArgs
import com.fappslab.rickandmortygraphql.navigation.DetailsNavigation

internal class DetailsNavigationImpl : DetailsNavigation {

    override fun create(args: CharacterArgs): Fragment =
        DetailsFragment.createFragment(args)
}
