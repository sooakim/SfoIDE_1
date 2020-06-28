package io.github.sooakim.sfoide.view.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.sooakim.sfoide.BR
import io.github.sooakim.sfoide.utils.diff.Identifiable

abstract class BaseRecyclerViewAdapter<T: Identifiable>
    : ListAdapter<T, BaseRecyclerViewAdapter.BaseViewHolder<T>>(object: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.identifier == newItem.identifier
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onViewRecycled(holder: BaseViewHolder<T>) {
        holder.recycle()
    }

    open class BaseViewHolder<T>(
            private val binding: ViewDataBinding
    ): RecyclerView.ViewHolder(binding.root){
        open fun bind(item: T){
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }

        open fun recycle(){

        }
    }
}