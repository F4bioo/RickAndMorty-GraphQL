package com.fappslab.rickandmortygraphql.features.home.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.fappslab.rickandmortygraphql.core.common.domain.model.Character
import com.fappslab.rickandmortygraphql.core.common.domain.model.Characters
import com.fappslab.rickandmortygraphql.core.common.domain.model.Filter
import com.fappslab.rickandmortygraphql.core.common.domain.model.KeyType
import com.fappslab.rickandmortygraphql.core.common.domain.usecase.GetCharactersUseCase
import com.fappslab.rickandmortygraphql.core.common.domain.usecase.GetFilterUseCase
import com.fappslab.rickandmortygraphql.core.data.remote.client.network.exception.RemoteThrowable.ClientThrowable
import com.fappslab.rickandmortygraphql.core.data.remote.client.network.exception.RemoteThrowable.FilterThrowable
import com.fappslab.rickandmortygraphql.core.data.remote.client.network.exception.RemoteThrowable.ServerThrowable
import com.fappslab.rickandmortygraphql.libraries.arch.viewmodel.ViewModel
import com.fappslab.rickandmortygraphql.libraries.design.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val getFilterUseCase: GetFilterUseCase,
    private val getCharactersUseCase: GetCharactersUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel<HomeViewState, HomeViewAction>(HomeViewState()) {

    init {
        setFilter()
    }

    private fun getCharacters(filter: Filter) {
        getCharactersUseCase(filter)
            .flowOn(dispatcher)
            .onStart { onState { it.copy(shouldShowLoading = true, shouldShowTryAgain = false) } }
            .onCompletion { onState { it.copy(shouldShowLoading = false) } }
            .catch { getCharactersFailure(cause = it) }
            .onEach { getCharactersSuccess(characters = it) }
            .launchIn(viewModelScope)
    }

    private fun getCharactersFailure(cause: Throwable) {
        onState { it.getCharactersFailureState(errorMessageRes(cause)) }
    }

    private fun getCharactersSuccess(characters: Characters) {
        if (characters.characters.isNotEmpty()) {
            onState { it.getCharactersSuccessState(characters) }
        } else getCharactersFailure(FilterThrowable())
    }

    fun onPagination(shouldFetchNextPage: Boolean) {
        state.value.nextPageHandle(shouldFetchNextPage) { page ->
            getCharacters(state.value.filter.copy(page = page))
        }
    }

    fun onFabVisibility(shouldShowFabButton: Boolean) {
        onState { it.copy(shouldShowFabButton = shouldShowFabButton) }
    }

    fun onTryAgain() {
        if (state.value.errorMessageRes != R.string.common_filter_error) {
            getCharacters(state.value.filter)
        } else onFilter()
    }

    fun onFilter() {
        onAction { HomeViewAction.Filter }
    }

    fun onDetails(character: Character) {
        onState { it.copy(shouldShowDetails = true, character = character) }
    }

    fun onCloseDetails() {
        onState { it.copy(shouldShowDetails = false) }
    }

    fun onCloseError() {
        onState { it.copy(shouldShowError = false) }
    }

    fun setFilter() {
        viewModelScope.launch {
            val deferredList = KeyType.values().map { keyType ->
                async {
                    getFilterUseCase(keyType.name).collect { filterName ->
                        onState { it.filterUpdate(keyType, filterName) }
                    }
                }
            }
            deferredList.forEach { it.await() }

            onState { it.copy(characters = emptyList()) }
            getCharacters(state.value.filter)
        }
    }

    private fun errorMessageRes(cause: Throwable): Int = when (cause) {
        is ClientThrowable -> R.string.common_client_error
        is ServerThrowable -> R.string.common_server_error
        is FilterThrowable -> R.string.common_filter_error
        else -> R.string.common_unknown_error
    }
}
