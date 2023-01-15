package com.fappslab.rickandmortygraphql.home.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.fappslab.rickandmortygraphql.arch.viewmodel.ViewModel
import com.fappslab.rickandmortygraphql.domain.model.Character
import com.fappslab.rickandmortygraphql.domain.model.Characters
import com.fappslab.rickandmortygraphql.domain.model.Filter
import com.fappslab.rickandmortygraphql.domain.usecase.GetCharactersUseCase
import com.fappslab.rickandmortygraphql.remote.client.network.exception.RemoteThrowable.ClientThrowable
import com.fappslab.rickandmortygraphql.remote.client.network.exception.RemoteThrowable.FilterThrowable
import com.fappslab.rickandmortygraphql.remote.client.network.exception.RemoteThrowable.ServerThrowable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import com.fappslab.rickandmortygraphql.design.R as DS

internal class HomeViewModel(
    //private val getFilterUseCase: GetFilterUseCase,
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
        onState { it.getCharactersFailureState() }
        onError(cause)
    }

    private fun getCharactersSuccess(characters: Characters) {
        if (characters.characters.isNotEmpty()) {
            onState { it.getCharactersSuccessState(characters) }
        } else onError(FilterThrowable())
    }

    fun onPagination(shouldFetchNextPage: Boolean) {
        state.value.nextPageHandle(shouldFetchNextPage) { page ->
            getCharacters(state.value.filter.copy(page = page))
        }
    }

    fun onFabVisibility(shouldShowFabButton: Boolean) {
        onState { it.copy(shouldShowFabButton = shouldShowFabButton) }
    }

    fun onShowDetails(character: Character) {
        onState { it.copy(shouldShowDetails = true, character = character) }
    }

    fun onCloseDetails() {
        onState { it.copy(shouldShowDetails = false) }
    }

    fun onTryAgain() {
        getCharacters(state.value.filter)
    }

    fun onFilter() {
        onAction { HomeViewAction.Filter }
    }

    fun onDetails(character: Character) {
        onAction { HomeViewAction.Details(character) }
    }

    fun onCloseError(cause: Throwable) {
        if (cause is FilterThrowable) onFilter()
    }

    private fun onError(cause: Throwable) {
        onAction { HomeViewAction.Error(errorMessage(cause), cause) }
    }

    fun setFilter() {
        /*viewModelScope.launch {
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
        }*/
        getCharacters(state.value.filter)
    }

    private fun errorMessage(cause: Throwable): Int = when (cause) {
        is ClientThrowable -> DS.string.common_client_error
        is ServerThrowable -> DS.string.common_server_error
        is FilterThrowable -> DS.string.common_filter_error
        else -> DS.string.common_unknown_error
    }
}
