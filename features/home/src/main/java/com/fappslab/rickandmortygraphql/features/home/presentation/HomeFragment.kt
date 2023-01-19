package com.fappslab.rickandmortygraphql.features.home.presentation

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.fappslab.rickandmortygraphql.core.common.domain.model.Character
import com.fappslab.rickandmortygraphql.core.common.navigation.DetailsNavigation
import com.fappslab.rickandmortygraphql.core.common.navigation.FilterNavigation
import com.fappslab.rickandmortygraphql.features.home.R
import com.fappslab.rickandmortygraphql.features.home.databinding.HomeCharacterItemBinding
import com.fappslab.rickandmortygraphql.features.home.databinding.HomeFragmentBinding
import com.fappslab.rickandmortygraphql.features.home.presentation.extension.bind
import com.fappslab.rickandmortygraphql.features.home.presentation.extension.inflate
import com.fappslab.rickandmortygraphql.features.home.presentation.extension.loadingEmptyListVisibility
import com.fappslab.rickandmortygraphql.features.home.presentation.extension.loadingFooterVisibility
import com.fappslab.rickandmortygraphql.features.home.presentation.extension.showDetails
import com.fappslab.rickandmortygraphql.features.home.presentation.extension.showErrorDialog
import com.fappslab.rickandmortygraphql.features.home.presentation.extension.showFilter
import com.fappslab.rickandmortygraphql.features.home.presentation.extension.visibilityHandle
import com.fappslab.rickandmortygraphql.features.home.presentation.viewmodel.HomeViewAction
import com.fappslab.rickandmortygraphql.features.home.presentation.viewmodel.HomeViewModel
import com.fappslab.rickandmortygraphql.features.home.presentation.viewmodel.HomeViewState
import com.fappslab.rickandmortygraphql.features.home.presentation.viewmodel.PAGING_SPAN_COUNT_PORTRAIT
import com.fappslab.rickandmortygraphql.libraries.arch.adapter.GenericAdapter
import com.fappslab.rickandmortygraphql.libraries.arch.paging.pagingscroll.PagingScrollListener
import com.fappslab.rickandmortygraphql.libraries.arch.viewbinding.viewBinding
import com.fappslab.rickandmortygraphql.libraries.arch.viewmodel.onViewAction
import com.fappslab.rickandmortygraphql.libraries.arch.viewmodel.onViewState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

internal class HomeFragment : Fragment(R.layout.home_fragment) {

    private val binding: HomeFragmentBinding by viewBinding()
    private val viewModel: HomeViewModel by sharedViewModel()
    private val detailsNavigation: DetailsNavigation by inject()
    private val filterNavigation: FilterNavigation by inject()
    private val includeEmptyList get() = binding.includeEmptyList
    private val includeFooter get() = binding.includeFooter
    private val charactersAdapter by lazy {
        GenericAdapter<HomeCharacterItemBinding, Character>(::inflate) { binding, item, _ ->
            bind(binding, character = item, itemAction = viewModel::onDetails)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
        setupRecycler()
        setupListeners()
    }

    private fun setupObservables() {
        onViewState(viewModel) { state ->
            loadingState(state)
            fabButtonVisibilityState(state)
            submitListState(state.characters)
            tryAgainState(state.shouldShowTryAgain)
            emptyLayoutState(state.shouldShowEmptyLayout)
        }

        onViewAction(viewModel) { action ->
            when (action) {
                HomeViewAction.Filter -> showFilterAction()
                is HomeViewAction.Details -> showDetailsAction(action.character)
                is HomeViewAction.Error -> showErrorDialogAction(action.message, action.cause)
            }
        }
    }

    private fun setupListeners() = binding.run {
        includeFooter.buttonTryAgainFooter.setOnClickListener { viewModel.onTryAgain() }
        fabFilter.setOnClickListener { viewModel.onFilter() }
        recyclerCharacters.addOnScrollListener(
            PagingScrollListener(
                shouldCallNextPage = viewModel::onPagination,
                shouldShowFabButton = viewModel::onFabVisibility,
            )
        )
    }

    private fun setupRecycler() = binding.recyclerCharacters.run {
        setHasFixedSize(true)
        itemAnimator = null
        adapter = charactersAdapter
        layoutManager = GridLayoutManager(context, PAGING_SPAN_COUNT_PORTRAIT)
    }

    private fun loadingState(state: HomeViewState) {
        binding.loadingEmpty.loadingEmptyListVisibility(state)
        includeFooter.loadingFooter.loadingFooterVisibility(state)
    }

    private fun fabButtonVisibilityState(state: HomeViewState) = state.run {
        binding.fabFilter.visibilityHandle { shouldShowFabButton and characters.isNotEmpty() }
    }

    private fun tryAgainState(isVisible: Boolean) {
        includeFooter.buttonTryAgainFooter.isVisible = isVisible
    }

    private fun emptyLayoutState(isVisible: Boolean) {
        includeEmptyList.root.isVisible = isVisible
    }

    private fun submitListState(characters: List<Character>) {
        charactersAdapter.submitList(characters)
    }

    private fun showFilterAction() {
        showFilter(filterNavigation, closeAction = viewModel::setFilter)
    }

    private fun showDetailsAction(character: Character) {
        showDetails(character, detailsNavigation)
    }

    private fun showErrorDialogAction(@StringRes messageRes: Int, cause: Throwable) {
        showErrorDialog(messageRes, cause, closeAction = viewModel::onCloseError)
    }

    companion object {
        fun create(): HomeFragment = HomeFragment()
    }
}
