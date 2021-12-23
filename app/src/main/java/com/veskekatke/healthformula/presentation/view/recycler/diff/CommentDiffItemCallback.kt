package com.veskekatke.healthformula.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.veskekatke.healthformula.data.models.post.Comment
import com.veskekatke.healthformula.data.models.post.Post

class CommentDiffItemCallback : DiffUtil.ItemCallback<Comment>(){
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return true
    }

}