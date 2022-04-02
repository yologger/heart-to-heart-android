package com.yologger.presentation.screen.main.more.showMyPosts

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MyPostsInfiniteScrollListener(
    private val layoutManager: RecyclerView.LayoutManager,
    private val viewModel: ShowMyPostsViewModel
): RecyclerView.OnScrollListener() {

    private var totalItemCount: Int = 0
    private var lastVisibleItemPosition: Int = 0
    // private var visibleThreshold: Int = 5
    private var visibleThreshold: Int = 3

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy < 0) return

        totalItemCount = layoutManager.itemCount

        if (layoutManager is StaggeredGridLayoutManager) {
            //
        } else if (layoutManager is GridLayoutManager) {
            //
        } else if (layoutManager is LinearLayoutManager) {
            lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        }

        if (!viewModel.liveIsLoading.value!! && totalItemCount <= lastVisibleItemPosition + visibleThreshold) viewModel.loadData()
    }
}