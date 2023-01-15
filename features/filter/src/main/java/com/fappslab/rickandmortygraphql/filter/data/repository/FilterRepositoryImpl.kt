package com.fappslab.rickandmortygraphql.filter.data.repository

import com.fappslab.rickandmortygraphql.filter.data.source.FilterDataSource
import com.fappslab.rickandmortygraphql.filter.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow

internal class FilterRepositoryImpl(
    private val dataSource: FilterDataSource
) : FilterRepository {

    override fun getFilter(prefsKey: String): Flow<String?> =
        dataSource.getFilter(prefsKey)

    override fun setFilter(prefsKey: String, value: String): Flow<Unit> =
        dataSource.setFilter(prefsKey, value)

    override fun deleteFilter(prefsKey: String): Flow<Unit> =
        dataSource.deleteFilter(prefsKey)
}
