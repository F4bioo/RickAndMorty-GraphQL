package com.fappslab.rickandmortygraphql.home.presentation.viewmodel

import com.fappslab.rickandmortygraphql.arch.extension.orZero
import com.fappslab.rickandmortygraphql.domain.model.Character
import com.fappslab.rickandmortygraphql.domain.model.Characters
import com.fappslab.rickandmortygraphql.domain.model.Filter
import com.fappslab.rickandmortygraphql.domain.model.KeyType

internal const val FIRST_PAGE = 1
internal const val PAGING_SPAN_COUNT_PORTRAIT = 3
internal const val PAGING_SPAN_COUNT_LANDSCAPE = 6

internal data class HomeViewState(
    val character: Character? = null,
    val shouldShowTryAgain: Boolean = false,
    val shouldShowLoading: Boolean = false,
    val shouldShowDetails: Boolean = false,
    val shouldShowEmptyLayout: Boolean = false,
    val shouldShowFabButton: Boolean = true,
    val characters: List<Character> = emptyList(),
    val filter: Filter = Filter(),
    val totalPages: Int? = null
) {

    fun getCharactersFailureState() = copy(
        shouldShowEmptyLayout = characters.isEmpty(),
        shouldShowTryAgain = true
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

    private fun unionList(newList: List<Character>): List<Character> {
        return characters.union(newList).toList()
    }

    fun filterUpdate(keyType: KeyType, filterName: String?) = when (keyType) {
        KeyType.KeyStatus -> copy(filter = filter.copy(status = filterName, page = FIRST_PAGE))
        KeyType.KeyGender -> copy(filter = filter.copy(gender = filterName, page = FIRST_PAGE))
        KeyType.KeySpecies -> copy(filter = filter.copy(species = filterName, page = FIRST_PAGE))
    }
}
