package io.github.sooakim.sfoide.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import io.github.sooakim.sfoide.databinding.ItemUserListBinding
import io.github.sooakim.sfoide.view.base.BaseRecyclerViewAdapter
import io.github.sooakim.sfoide.view.main.model.UserUiModel

class UserListAdapter: BaseRecyclerViewAdapter<UserUiModel>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<UserUiModel> {
        return ItemViewHolder(
                ItemUserListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    class ItemViewHolder(
            private val binding: ItemUserListBinding
    ): BaseViewHolder<UserUiModel>(binding){
        override fun bind(item: UserUiModel) {
            super.bind(item)
            binding.root.setOnClickListener{ _ ->
                item.onClick.invoke()
            }
        }

        override fun recycle() {
            Glide.with(binding.ivProfile)
                    .clear(binding.ivProfile)
        }
    }
}