package io.github.sooakim.sfoide.utils.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.sooakim.sfoide.utils.diff.Identifiable
import io.github.sooakim.sfoide.utils.pagination.EndlessRecyclerViewScrollListener
import io.github.sooakim.sfoide.view.base.BaseRecyclerViewAdapter

@BindingAdapter("onLoadMore")
fun RecyclerView.bindOnLoadMore(listener: OnLoadMoreListener){
    this.addOnScrollListener(object: EndlessRecyclerViewScrollListener() {
        override fun onLoadMore(view: RecyclerView) {
            listener.onLoadMore()
        }
    })
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("list")
fun<T: Identifiable> RecyclerView.bindList(list: List<T>?){
    if(this.adapter is BaseRecyclerViewAdapter<*>){
        val adapter = this.adapter as BaseRecyclerViewAdapter<T>
        adapter.submitList(list)
    }
}

@FunctionalInterface
interface OnLoadMoreListener{
    fun onLoadMore()
}