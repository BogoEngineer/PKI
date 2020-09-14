package com.veskekatke.healthformula.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.post.Post
import com.veskekatke.healthformula.presentation.view.recycler.diff.PostDiffItemCallback
import com.veskekatke.healthformula.presentation.view.recycler.viewholder.PostViewHolder

class PostAdapter(
    postDiffItemCallback: PostDiffItemCallback,
    private val onPostClicked: (Post) -> Unit) : ListAdapter<Post, PostViewHolder>(postDiffItemCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.layout_post_list_item, parent, false)
        return PostViewHolder(containerView) {
            val post = getItem(it)
            onPostClicked.invoke(post)
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }


}