package com.fappslab.rickandmortygraphql.arch.paging.pagingscroll

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PagingScrollListener(
    private val shouldCallNextPage: (Boolean) -> Unit = {},
    private val shouldShowFabButton: (Boolean) -> Unit = {}
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
        val totalItemCount = layoutManager.itemCount
        shouldCallNextPage(lastVisibleItem == totalItemCount.dec())
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        shouldShowFabButton(newState == RecyclerView.SCROLL_STATE_IDLE)
    }
}
