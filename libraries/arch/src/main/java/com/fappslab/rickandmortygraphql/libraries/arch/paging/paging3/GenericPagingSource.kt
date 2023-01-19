package com.fappslab.rickandmortygraphql.libraries.arch.paging.paging3

import androidx.paging.PagingSource
import androidx.paging.PagingState

class GenericPagingSource<T : Any>(
    private val items: suspend (Int) -> Pair<List<T>, Int>
) : PagingSource<Int, T>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, T> = runCatching {
        val page = params.key ?: 1
        val (items, totalPages) = items(page)
        getLoadResult(items, page, totalPages)
    }.getOrElse { LoadResult.Error(it) }

    override fun getRefreshKey(
        state: PagingState<Int, T>
    ): Int? = state.anchorPosition?.let { anchorPosition ->
        val anchorPage = state.closestPageToPosition(anchorPosition)
        anchorPage?.prevKey?.inc() ?: anchorPage?.nextKey?.dec()
    }

    private fun getLoadResult(
        items: List<T>,
        page: Int,
        totalPages: Int
    ): LoadResult<Int, T> = LoadResult.Page(
        data = items,
        nextKey = nextKey(page, totalPages),
        prevKey = null
    )

    private fun nextKey(
        page: Int,
        totalPages: Int
    ): Int? = if (page < totalPages) {
        page.inc()
    } else null
}
