package com.fappslab.rickandmortygraphql.home.presentation.viewmodel

import com.fappslab.rickandmortygraphql.arch.extension.orZero
import com.fappslab.rickandmortygraphql.domain.model.Character
import com.fappslab.rickandmortygraphql.domain.model.Characters

internal const val FIRST_PAGE = 1
internal const val PAGING_SPAN_COUNT_PORTRAIT = 3
internal const val PAGING_SPAN_COUNT_LANDSCAPE = 6

internal data class HomeViewState(
    val character: Character? = null,
    val shouldShowTryAgain: Boolean = false,
    val shouldShowLoading: Boolean = false,
    val shouldShowDetails: Boolean = false,
    val shouldShowEmptyLayout: Boolean = false,
    val characters: List<Character> = emptyList(),
    val totalPages: Int? = null,
    val nextPage: Int = FIRST_PAGE
) {

    fun getCharactersFailureState() = copy(
        shouldShowEmptyLayout = characters.isEmpty(),
        shouldShowTryAgain = true
    )

    fun getCharactersSuccessState(newCharacters: Characters) = copy(
        shouldShowEmptyLayout = false,
        shouldShowTryAgain = false,
        characters = unionList(newList = newCharacters.characters),
        totalPages = newCharacters.totalPages,
        nextPage = nextPage.inc()
    )

    fun nextPageHandle(
        shouldFetchNextPage: Boolean,
        fetchNextPage: (Int) -> Unit
    ) {
        if (
            shouldFetchNextPage and
            shouldShowLoading.not() and
            shouldShowTryAgain.not() and
            (nextPage <= totalPages.orZero())
        ) fetchNextPage(nextPage)
    }

    private fun unionList(newList: List<Character>): List<Character> {
        return characters.union(newList).toList()
    }
}
