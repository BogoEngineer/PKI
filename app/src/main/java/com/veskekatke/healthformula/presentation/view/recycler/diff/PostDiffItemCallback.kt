package com.veskekatke.healthformula.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.veskekatke.healthformula.data.models.post.Post

class PostDiffItemCallback : DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return true // post cant be changed by user
    }

}