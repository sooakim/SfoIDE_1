package io.github.sooakim.sfoide.utils.pagination

import android.widget.AbsListView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class EndlessRecyclerViewScrollListener : RecyclerView.OnScrollListener() {
    private var previousTotalItemCount: Int = 0
    private var isLoading: Boolean = true

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        lastVisibleItemPositions.indices.forEach {
            if (it == 0)
                maxSize = lastVisibleItemPositions[it]
            else if (lastVisibleItemPositions[it] > maxSize)
                maxSize = lastVisibleItemPositions[it]
        }
        return maxSize
    }

    private fun getLastVisibleItemPosition(recyclerView: RecyclerView): Int? {
        return when {
            recyclerView.layoutManager is LinearLayoutManager ->
                (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            recyclerView.layoutManager is GridLayoutManager ->
                (recyclerView.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
            recyclerView.layoutManager is StaggeredGridLayoutManager ->
                getLastVisibleItem(
                        (recyclerView.labelFor as StaggeredGridLayoutManager).findLastVisibleItemPositions(
                                null
                        )
                )
            else -> null
        }
    }

    private fun getTotalItemCount(recyclerView: RecyclerView): Int? {
        return when {
            recyclerView.layoutManager is LinearLayoutManager ->
                (recyclerView.layoutManager as LinearLayoutManager).itemCount
            recyclerView.layoutManager is GridLayoutManager ->
                (recyclerView.layoutManager as GridLayoutManager).itemCount
            recyclerView.layoutManager is StaggeredGridLayoutManager ->
                (recyclerView.layoutManager as StaggeredGridLayoutManager).itemCount
            else -> null
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemCount = getTotalItemCount(recyclerView) ?: return
        val lastVisibleItemPosition = getLastVisibleItemPosition(recyclerView) ?: return

        if (totalItemCount < previousTotalItemCount) {
            this.previousTotalItemCount = totalItemCount
            this.isLoading = (totalItemCount == 0)
        }

        if (isLoading && (totalItemCount > previousTotalItemCount)) {
            this.isLoading = false
            this.previousTotalItemCount = totalItemCount
        }

        if (!isLoading && (lastVisibleItemPosition + VISIBLE_THRESHOLD) > totalItemCount) {
            this.onLoadMore(recyclerView)
            this.isLoading = true
        }
    }

    abstract fun onLoadMore(view: RecyclerView)

    companion object {
        const val VISIBLE_THRESHOLD = 10
    }
}