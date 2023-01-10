package com.fappslab.rickandmortygraphql.home.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.fappslab.rickandmortygraphql.arch.viewmodel.ViewModel
import com.fappslab.rickandmortygraphql.domain.model.Character
import com.fappslab.rickandmortygraphql.domain.model.Characters
import com.fappslab.rickandmortygraphql.domain.usecase.GetCharactersUseCase
import com.fappslab.rickandmortygraphql.remote.client.network.exception.RemoteThrowable.ClientThrowable
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
    private val getCharactersUseCase: GetCharactersUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel<HomeViewState, HomeViewAction>(HomeViewState()) {

    init {
        getCharacters(page = FIRST_PAGE)
    }

    private fun getCharacters(page: Int) {
        getCharactersUseCase(page)
            .flowOn(dispatcher)
            .onStart { onState { it.copy(shouldShowLoading = true, shouldShowTryAgain = false) } }
            .onCompletion { onState { it.copy(shouldShowLoading = false) } }
            .catch { getCharactersFailure(cause = it) }
            .onEach { getCharactersSuccess(characters = it) }
            .launchIn(viewModelScope)
    }

    private fun getCharactersFailure(cause: Throwable) {
        onState { it.getCharactersFailureState() }
        onAction { HomeViewAction.Error(errorMessage(cause)) }
    }

    private fun getCharactersSuccess(characters: Characters) {
        onState { it.getCharactersSuccessState(characters) }
    }

    fun onPagination(shouldFetchNextPage: Boolean) {
        state.value.nextPageHandle(shouldFetchNextPage) { page ->
            getCharacters(page)
        }
    }

    fun onShowDetails(character: Character) {
        onState { it.copy(shouldShowDetails = true, character = character) }
    }

    fun onCloseDetails() {
        onState { it.copy(shouldShowDetails = false) }
    }

    fun onTryAgain() {
        getCharacters(page = state.value.nextPage)
    }

    private fun errorMessage(cause: Throwable): Int = when (cause) {
        is ClientThrowable -> DS.string.common_client_error
        is ServerThrowable -> DS.string.common_server_error
        else -> DS.string.common_unknown_error
    }
}
