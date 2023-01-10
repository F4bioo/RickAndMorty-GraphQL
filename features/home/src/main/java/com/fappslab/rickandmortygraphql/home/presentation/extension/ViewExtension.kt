package com.fappslab.rickandmortygraphql.home.presentation.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import coil.load
import com.fappslab.rickandmortygraphql.design.dsdialogsm.build
import com.fappslab.rickandmortygraphql.design.dsdialogsm.dsDialogSm
import com.fappslab.rickandmortygraphql.design.dsmodal.build
import com.fappslab.rickandmortygraphql.design.dsmodal.dsModalHost
import com.fappslab.rickandmortygraphql.home.databinding.HomeCharacterItemBinding
import com.fappslab.rickandmortygraphql.home.presentation.HomeFragment
import com.fappslab.rickandmortygraphql.home.presentation.model.StatusType.Alive
import com.fappslab.rickandmortygraphql.home.presentation.model.StatusType.Dead
import com.fappslab.rickandmortygraphql.home.presentation.model.StatusType.Unknown
import com.fappslab.rickandmortygraphql.home.presentation.viewmodel.HomeViewState
import com.fappslab.rickandmortygraphql.design.R as DS

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

internal fun HomeFragment.showErrorDialog(@StringRes stringRes: Int) {
    dsDialogSm {
        titleRes = DS.string.common_error
        messageRes = stringRes
        shouldBlock = true
    }.build(childFragmentManager)
}

internal fun HomeFragment.showDetails() {
    dsModalHost {
        setFragment = { HomeFragment() }
    }.build(childFragmentManager)
}
