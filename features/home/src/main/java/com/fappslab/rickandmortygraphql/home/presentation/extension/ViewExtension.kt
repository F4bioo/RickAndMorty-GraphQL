package com.fappslab.rickandmortygraphql.home.presentation.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import coil.load
import com.fappslab.rickandmortygraphql.libraries.design.dsdialogsm.build
import com.fappslab.rickandmortygraphql.libraries.design.dsdialogsm.dsDialogSm
import com.fappslab.rickandmortygraphql.libraries.design.dsmodal.build
import com.fappslab.rickandmortygraphql.libraries.design.dsmodal.dsModalHost
import com.fappslab.rickandmortygraphql.domain.model.Character
import com.fappslab.rickandmortygraphql.home.R
import com.fappslab.rickandmortygraphql.home.databinding.HomeCharacterItemBinding
import com.fappslab.rickandmortygraphql.home.presentation.HomeFragment
import com.fappslab.rickandmortygraphql.home.presentation.model.StatusType.Alive
import com.fappslab.rickandmortygraphql.home.presentation.model.StatusType.Dead
import com.fappslab.rickandmortygraphql.home.presentation.model.StatusType.Unknown
import com.fappslab.rickandmortygraphql.home.presentation.model.extension.toCharacterArgs
import com.fappslab.rickandmortygraphql.home.presentation.viewmodel.HomeViewState
import com.fappslab.rickandmortygraphql.navigation.DetailsNavigation
import com.fappslab.rickandmortygraphql.navigation.FilterNavigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.fappslab.rickandmortygraphql.libraries.design.R as DS

internal fun HomeFragment.bind(
    binding: HomeCharacterItemBinding,
    character: Character,
    itemAction: (Character) -> Unit
) {
    binding.cardCharacter.setOnClickListener { itemAction(character) }
    binding.textId.text = getString(R.string.home_character_id, character.id)
    binding.textCharacter.text = character.name
    binding.imageStatus.imageStatus(character.status)
    binding.imageCharacter.load(character.image) {
        crossfade(enable = true)
        placeholder(DS.drawable.placeholder)
        error(DS.drawable.placeholder)
    }
}

internal fun HomeFragment.inflate(parent: ViewGroup): HomeCharacterItemBinding =
    LayoutInflater.from(context).let { inflater ->
        HomeCharacterItemBinding.inflate(inflater, parent, false)
    }

internal fun ImageView.imageStatus(status: String) {
    val iconRes = when (status) {
        Alive.name -> Alive.iconRes
        Dead.name -> Dead.iconRes
        else -> Unknown.iconRes
    }
    load(iconRes)
}

internal fun ProgressBar.loadingFooterVisibility(state: HomeViewState) = state.run {
    isVisible = shouldShowLoading and shouldShowTryAgain.not() and characters.isNotEmpty()
}

internal fun ProgressBar.loadingEmptyListVisibility(state: HomeViewState) = state.run {
    isVisible = shouldShowTryAgain.not() and characters.isEmpty()
}

internal fun HomeFragment.showErrorDialog(
    @StringRes stringRes: Int,
    cause: Throwable,
    closeAction: (Throwable) -> Unit
) {
    dsDialogSm {
        titleRes = DS.string.common_error
        messageRes = stringRes
        shouldBlock = true
        onCloseButton = { closeAction(cause) }
    }.build(childFragmentManager)
}

internal fun HomeFragment.showDetails(character: Character, navigation: DetailsNavigation) {
    dsModalHost {
        setFragment = { navigation.create(character.toCharacterArgs()) }
    }.build(childFragmentManager)
}

internal fun HomeFragment.showFilter(navigation: FilterNavigation, closeAction: () -> Unit) {
    dsModalHost {
        setFragment = { navigation.create() }
        onCloseButton = { closeAction() }
        shouldBlock = true
        shouldExpanded = true
    }.build(childFragmentManager)
}

internal fun FloatingActionButton.visibilityHandle(isVisible: () -> Boolean) {
    if (isVisible()) {
        show()
    } else hide()
}
