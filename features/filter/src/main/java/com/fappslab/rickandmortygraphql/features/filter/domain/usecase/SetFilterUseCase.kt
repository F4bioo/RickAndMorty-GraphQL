package com.fappslab.rickandmortygraphql.features.filter.domain.usecase

import com.fappslab.rickandmortygraphql.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow

internal class SetFilterUseCase(
    private val repository: RickAndMortyRepository
) {

    operator fun invoke(prefsKey: String, value: String): Flow<Unit> =
        repository.setFilter(prefsKey, value)
}
