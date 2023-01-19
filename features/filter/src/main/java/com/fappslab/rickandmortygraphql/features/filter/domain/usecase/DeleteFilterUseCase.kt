package com.fappslab.rickandmortygraphql.features.filter.domain.usecase

import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow

internal class DeleteFilterUseCase(
    private val repository: RickAndMortyRepository
) {

    operator fun invoke(prefsKey: String): Flow<Unit> =
        repository.deleteFilter(prefsKey)
}
