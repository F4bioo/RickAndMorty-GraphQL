package com.fappslab.rickandmortygraphql.filter.domain.usecase

import com.fappslab.rickandmortygraphql.filter.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow

internal class GetFilterUseCase(
    private val repository: FilterRepository
) {

    operator fun invoke(prefsKey: String): Flow<String?> =
        repository.getFilter(prefsKey)
}
