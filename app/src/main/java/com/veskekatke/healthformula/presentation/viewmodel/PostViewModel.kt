package com.veskekatke.healthformula.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.veskekatke.healthformula.data.models.post.Post

class PostViewModel : ViewModel(){

    private val posts : MutableLiveData<List<Post>> = MutableLiveData()

    private val postList : MutableList<Post> = mutableListOf()

    init {
        for (i in 1..100) {
            val car = Post(
                i,
                "Ime $i",
                "https://electric-fun.com/wp-content/uploads/2020/01/sony-car-796x418-1.jpg",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
            )
            postList.add(car)
        }
        val listToSubmit = mutableListOf<Post>()
        listToSubmit.addAll(postList)
        posts.value = listToSubmit
    }

    fun getPosts() : LiveData<List<Post>>{
        return posts
    }

}