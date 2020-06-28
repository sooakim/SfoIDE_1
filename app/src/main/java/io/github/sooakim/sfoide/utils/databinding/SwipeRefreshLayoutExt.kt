package io.github.sooakim.sfoide.utils.databinding

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("onRefresh")
fun SwipeRefreshLayout.bindOnRefresh(listener: SwipeRefreshLayout.OnRefreshListener){
    this.setOnRefreshListener(listener)
}

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.bindRefreshing(isRefreshing: Boolean){
    this.isRefreshing = isRefreshing
}