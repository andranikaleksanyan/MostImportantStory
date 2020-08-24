package com.mostimportantstory.ui.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Karo.Hovhannisyan on 11, July, 2020
 **/
class BaseViewHolder<T>(val binding: ViewDataBinding, private var onItemClicked: ((T) -> Unit)?) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(item: T) {
        binding.root.setOnClickListener {
            onItemClicked?.invoke(item)
        }
        binding.setVariable(BR.itemRoot, this)
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()

    }
}