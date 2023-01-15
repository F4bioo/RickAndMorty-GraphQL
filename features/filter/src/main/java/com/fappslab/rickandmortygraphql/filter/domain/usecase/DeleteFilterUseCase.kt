package com.fappslab.rickandmortygraphql.filter.domain.usecase

import com.fappslab.rickandmortygraphql.filter.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow

internal class DeleteFilterUseCase(
    private val repository: FilterRepository
) {

    operator fun invoke(prefsKey: String): Flow<Unit> =
        repository.deleteFilter(prefsKey)
}
