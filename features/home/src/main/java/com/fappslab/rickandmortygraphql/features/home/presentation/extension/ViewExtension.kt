package com.fappslab.rickandmortygraphql.features.home.presentation.extension

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import coil.load
import com.fappslab.rickandmortygraphql.core.common.domain.model.Character
import com.fappslab.rickandmortygraphql.core.common.navigation.DetailsNavigation
import com.fappslab.rickandmortygraphql.core.common.navigation.FilterNavigation
import com.fappslab.rickandmortygraphql.features.home.R
import com.fappslab.rickandmortygraphql.features.home.databinding.HomeCharacterItemBinding
import com.fappslab.rickandmortygraphql.features.home.presentation.HomeFragment
import com.fappslab.rickandmortygraphql.features.home.presentation.model.StatusType.Alive
import com.fappslab.rickandmortygraphql.features.home.presentation.model.StatusType.Dead
import com.fappslab.rickandmortygraphql.features.home.presentation.model.StatusType.Unknown
import com.fappslab.rickandmortygraphql.features.home.presentation.model.extension.toCharacterArgs
import com.fappslab.rickandmortygraphql.features.home.presentation.viewmodel.HomeViewState
import com.fappslab.rickandmortygraphql.libraries.design.dsdialogsm.build
import com.fappslab.rickandmortygraphql.libraries.design.dsdialogsm.dsDialogSm
import com.fappslab.rickandmortygraphql.libraries.design.dsmodal.build
import com.fappslab.rickandmortygraphql.libraries.design.dsmodal.dsModalHost
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.fappslab.rickandmortygraphql.libraries.design.R as DS

private const val PAGING_SPAN_COUNT_PORTRAIT = 3
private const val PAGING_SPAN_COUNT_LANDSCAPE = 6

internal fun HomeFragment.bind(
    binding: HomeCharacterItemBinding,
    character: Character,
    itemAction: (Character) -> Unit
) = binding.run {
    textId.text = getString(R.string.home_character_id, character.id)
    textCharacter.text = character.name
    imageStatus.imageStatus(character.status)
    imageCharacter.load(character.image) {
        crossfade(enable = true)
        placeholder(DS.drawable.placeholder)
        error(DS.drawable.placeholder)
    }
    cardCharacter.setOnClickListener {
        itemAction(character)
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

internal fun HomeFragment.spanCount(): Int =
    if (isLandscapeMode()) {
        PAGING_SPAN_COUNT_LANDSCAPE
    } else PAGING_SPAN_COUNT_PORTRAIT

internal fun ProgressBar.loadingEmptyListVisibility(state: HomeViewState) = state.run {
    isVisible = shouldShowTryAgain.not() and
            shouldShowEmptyLayout.not() and
            shouldShowError.not() and
            characters.isEmpty()
}

internal fun HomeFragment.showErrorDialog(
    errorMessageRes: Int,
    shouldShowError: Boolean,
    closeAction: () -> Unit
) {
    dsDialogSm {
        titleRes = DS.string.common_error
        messageRes = errorMessageRes
        shouldBlock = true
        onCloseButton = closeAction
    }.build(shouldShowError, childFragmentManager)
}

internal fun HomeFragment.showDetails(
    character: Character,
    navigation: DetailsNavigation,
    shouldShowDetails: Boolean,
    closeAction: () -> Unit
) {
    dsModalHost {
        setFragment = { navigation.create(character.toCharacterArgs()) }
        shouldBlock = true
        onCloseButton = closeAction
        shouldExpanded = isLandscapeMode()
    }.build(shouldShowDetails, childFragmentManager)
}

internal fun HomeFragment.showFilter(navigation: FilterNavigation, closeAction: () -> Unit) {
    dsModalHost {
        setFragment = { navigation.create() }
        onCloseButton = closeAction
        shouldBlock = true
        shouldExpanded = true
    }.build(shouldShow = true, childFragmentManager)
}

internal fun FloatingActionButton.visibilityHandle(isVisible: () -> Boolean) {
    if (isVisible()) show() else hide()
}

private fun HomeFragment.isLandscapeMode(): Boolean =
    resources.configuration.orientation == ORIENTATION_LANDSCAPE
