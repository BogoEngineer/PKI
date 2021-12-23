package com.veskekatke.healthformula.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.post.Comment
import com.veskekatke.healthformula.presentation.view.recycler.diff.CommentDiffItemCallback
import com.veskekatke.healthformula.presentation.view.recycler.viewholder.CommentViewHolder

class CommentAdapter(
    commentDiffItemCallback: CommentDiffItemCallback,
    private val onCommentClicked: (Comment) -> Unit) : ListAdapter<Comment, CommentViewHolder>(commentDiffItemCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.layout_comment_list_item, parent, false)
        return CommentViewHolder(containerView) {
            val comment = getItem(it)
            onCommentClicked.invoke(comment)
        }
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment)
    }


}