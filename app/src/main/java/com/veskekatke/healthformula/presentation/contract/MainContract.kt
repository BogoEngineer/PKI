package com.veskekatke.healthformula.presentation.contract

import androidx.lifecycle.LiveData
import com.veskekatke.healthformula.data.models.user.UserResponse
import com.veskekatke.healthformula.presentation.view.states.PostsState
import com.veskekatke.healthformula.presentation.view.states.SupplementsState

interface MainContract {
    interface PostViewModel {
        val postsState: LiveData<PostsState>
        fun fetchAllPosts()
        fun getAllPosts()
    }

    interface SupplementViewModel {
        val supplementsState: LiveData<SupplementsState>
        fun fetchAllSupplements()
        fun getAllSupplements()
        fun getSupplementsByName(name : String)
    }

    interface UserViewModel {
        val user: LiveData<UserResponse>
        fun fetch()
        fun get()
        fun getFoodItemsByName(filter : String)
    }
}