package com.fappslab.rickandmortygraphql.filter.domain.usecase

import com.fappslab.rickandmortygraphql.filter.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow

internal class SetFilterUseCase(
    private val repository: FilterRepository
) {

    operator fun invoke(prefsKey: String, value: String): Flow<Unit> =
        repository.setFilter(prefsKey, value)
}
