package com.veskekatke.healthformula.presentation.contract

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.veskekatke.healthformula.data.datasources.ServerAuthenticateResponse
import com.veskekatke.healthformula.data.models.user.Credentials
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
        val loggedIn: LiveData<ServerAuthenticateResponse>
        fun fetch(userId: String)
        fun get()
        fun getFoodItemsByName(filter : String)
        fun authenticate(credentials: Credentials)
        fun logOut()
    }
}