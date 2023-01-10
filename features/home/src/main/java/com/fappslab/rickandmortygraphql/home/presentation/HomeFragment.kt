package com.fappslab.rickandmortygraphql.home.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.fappslab.rickandmortygraphql.arch.adapter.GenericAdapter
import com.fappslab.rickandmortygraphql.arch.paging.pagingscroll.PagingScrollListener
import com.fappslab.rickandmortygraphql.arch.viewbinding.viewBinding
import com.fappslab.rickandmortygraphql.arch.viewmodel.onViewAction
import com.fappslab.rickandmortygraphql.arch.viewmodel.onViewState
import com.fappslab.rickandmortygraphql.domain.model.Character
import com.fappslab.rickandmortygraphql.home.R
import com.fappslab.rickandmortygraphql.home.databinding.HomeCharacterItemBinding
import com.fappslab.rickandmortygraphql.home.databinding.HomeFragmentBinding
import com.fappslab.rickandmortygraphql.home.presentation.extension.imageStatus
import com.fappslab.rickandmortygraphql.home.presentation.extension.inflate
import com.fappslab.rickandmortygraphql.home.presentation.extension.loadingEmptyListVisibility
import com.fappslab.rickandmortygraphql.home.presentation.extension.loadingFooterVisibility
import com.fappslab.rickandmortygraphql.home.presentation.extension.showDetails
import com.fappslab.rickandmortygraphql.home.presentation.extension.showErrorDialog
import com.fappslab.rickandmortygraphql.home.presentation.viewmodel.HomeViewAction
import com.fappslab.rickandmortygraphql.home.presentation.viewmodel.HomeViewModel
import com.fappslab.rickandmortygraphql.home.presentation.viewmodel.HomeViewState
import com.fappslab.rickandmortygraphql.home.presentation.viewmodel.PAGING_SPAN_COUNT_PORTRAIT
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.fappslab.rickandmortygraphql.design.R as DS

internal class HomeFragment : Fragment(R.layout.home_fragment) {

    private val binding: HomeFragmentBinding by viewBinding()
    private val viewModel: HomeViewModel by sharedViewModel()
    private val includeEmptyList get() = binding.includeEmptyList
    private val includeFooter get() = binding.includeFooter
    private val charactersAdapter by lazy {
        GenericAdapter<HomeCharacterItemBinding, Character>(::inflate) { binding, item, _ ->
            binding.bind(character = item)
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
            submitListState(state.characters)
            tryAgainState(state.shouldShowTryAgain)
            emptyLayoutState(state.shouldShowEmptyLayout)
        }

        onViewAction(viewModel) { action ->
            when (action) {
                is HomeViewAction.Error -> showErrorDialog(action.message)
            }
        }
    }

    private fun setupListeners() {
        includeFooter.buttonTryAgainFooter.setOnClickListener { viewModel.onTryAgain() }
        binding.recyclerCharacters.addOnScrollListener(PagingScrollListener(viewModel::onPagination))
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

    private fun tryAgainState(isVisible: Boolean) {
        includeFooter.buttonTryAgainFooter.isVisible = isVisible
    }

    private fun emptyLayoutState(isVisible: Boolean) {
        includeEmptyList.root.isVisible = isVisible
    }

    private fun submitListState(characters: List<Character>) {
        charactersAdapter.submitList(characters)
    }

    private fun HomeCharacterItemBinding.bind(character: Character) {
        cardCharacter.setOnClickListener { /* TODO Implement - next PR*/ }
        textId.text = getString(R.string.home_character_id, character.id)
        textCharacter.text = character.name
        imageStatus.imageStatus(character.status)
        imageCharacter.load(character.image) {
            crossfade(enable = true)
            placeholder(DS.drawable.placeholder)
            error(DS.drawable.placeholder)
        }
    }

    companion object {
        fun createFragment(): HomeFragment = HomeFragment()
    }
}
