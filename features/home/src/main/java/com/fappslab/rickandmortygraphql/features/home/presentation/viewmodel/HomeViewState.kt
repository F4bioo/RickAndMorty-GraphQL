package com.fappslab.rickandmortygraphql.features.home.presentation.viewmodel

import com.fappslab.rickandmortygraphql.core.common.domain.model.Character
import com.fappslab.rickandmortygraphql.core.common.domain.model.Characters
import com.fappslab.rickandmortygraphql.core.common.domain.model.Filter
import com.fappslab.rickandmortygraphql.core.common.domain.model.KeyType
import com.fappslab.rickandmortygraphql.libraries.arch.extension.orZero
import com.fappslab.rickandmortygraphql.libraries.design.R

internal const val FIRST_PAGE = 1

internal data class HomeViewState(
    val shouldShowDetails: Boolean = false,
    val character: Character? = null,
    val shouldShowTryAgain: Boolean = false,
    val shouldShowLoading: Boolean = false,
    val shouldShowEmptyLayout: Boolean = false,
    val shouldShowFabButton: Boolean = true,
    val characters: List<Character> = emptyList(),
    val filter: Filter = Filter(),
    val totalPages: Int? = null,
    val shouldShowError: Boolean = false,
    val errorMessageRes: Int = R.string.common_unknown_error,
) {

    fun getCharactersFailureState(errorMessageRes: Int) = copy(
        shouldShowEmptyLayout = characters.isEmpty(),
        shouldShowTryAgain = true,
        shouldShowError = true,
        errorMessageRes = errorMessageRes
    )

    fun getCharactersSuccessState(newCharacters: Characters) = copy(
        shouldShowEmptyLayout = false,
        shouldShowTryAgain = false,
        shouldShowFabButton = true,
        characters = unionList(newList = newCharacters.characters),
        totalPages = newCharacters.totalPages,
        filter = filter.copy(page = filter.page.inc())
    )

    fun nextPageHandle(
        shouldFetchNextPage: Boolean,
        fetchNextPage: (Int) -> Unit
    ) {
        if (
            shouldFetchNextPage and
            shouldShowLoading.not() and
            shouldShowTryAgain.not() and
            (filter.page <= totalPages.orZero())
        ) fetchNextPage(filter.page)
    }

    fun filterUpdate(keyType: KeyType, filterName: String?) = when (keyType) {
        KeyType.KeyStatus -> copy(filter = filter.copy(status = filterName, page = FIRST_PAGE))
        KeyType.KeyGender -> copy(filter = filter.copy(gender = filterName, page = FIRST_PAGE))
        KeyType.KeySpecies -> copy(filter = filter.copy(species = filterName, page = FIRST_PAGE))
    }

    private fun unionList(newList: List<Character>): List<Character> {
        return characters.union(newList).toList()
    }
}
