package com.veskekatke.healthformula.presentation.view.states

import com.veskekatke.healthformula.data.models.post.Post

sealed class PostsState {
    object Loading: PostsState()
    object DataFetched: PostsState()
    data class Success(val posts: List<Post>): PostsState()
    data class Error(val message: String): PostsState()
}