package com.mostimportantstory.ui.scenes.adapter

import androidx.recyclerview.widget.DiffUtil
import com.mostimportantstory.R
import com.mostimportantstory.data.model.Chapter
import com.mostimportantstory.databinding.ItemSceneBinding
import com.mostimportantstory.ui.base.adapter.BaseAdapter
import com.mostimportantstory.ui.base.adapter.BaseViewHolder

/**
 * Created by Karo.Hovhannisyan on 11, July, 2020
 **/

class ScenesAdapter(val itemWidth: Int) : BaseAdapter<Chapter>(DiffCallback()) {


    init {
        setHasStableIds(true)
    }

    class DiffCallback : DiffUtil.ItemCallback<Chapter>() {
        override fun areItemsTheSame(
            oldItem: Chapter,
            newItem: Chapter
        ): Boolean {
            return oldItem.scenePosition == newItem.scenePosition
        }

        override fun areContentsTheSame(
            oldItem: Chapter,
            newItem: Chapter
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_scene

    override fun onBindViewHolder(holder: BaseViewHolder<Chapter>, position: Int) {
        if (holder.binding is ItemSceneBinding) {
            holder.binding.item = getItem(position)
            holder.binding.clItemRoot.layoutParams.width = itemWidth
            holder.binding.ivChapter.setImageResource(getItem(position).drawableRes)
        }

        return holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}