package com.fappslab.rickandmortygraphql.home.presentation.viewmodel

import com.fappslab.rickandmortygraphql.arch.extension.orZero
import com.fappslab.rickandmortygraphql.domain.model.Character
import com.fappslab.rickandmortygraphql.domain.model.Characters

internal const val FIRST_PAGE = 1

internal data class HomeViewState(
    val character: Character? = null,
    val shouldShowLoading: Boolean = false,
    val shouldShowDetails: Boolean = false,
    val characters: Set<Character> = emptySet(),
    val totalPages: Int? = null,
    val nextPage: Int = FIRST_PAGE
) {

    fun submitLIst(newCharacters: Characters) = copy(
        characters = newCharacters.characters join characters,
        totalPages = newCharacters.totalPages,
        nextPage = nextPage.inc()
    )

    fun nextPageHandle(
        shouldFetchNextPage: Boolean,
        fetchNextPage: (Int) -> Unit
    ) {
        if (shouldFetchNextPage && nextPage.inc() <= totalPages.orZero()) {
            fetchNextPage(nextPage)
        }
    }

    private infix fun List<Character>.join(
        oldList: Set<Character>
    ): Set<Character> = oldList + this
}
