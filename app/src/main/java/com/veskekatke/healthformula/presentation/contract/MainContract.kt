package com.veskekatke.healthformula.presentation.contract

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.veskekatke.healthformula.data.datasources.ServerAuthenticateResponse
import com.veskekatke.healthformula.data.datasources.ServerChangePasswordResponse
import com.veskekatke.healthformula.data.models.user.Credentials
import com.veskekatke.healthformula.data.models.user.PasswordInformation
import com.veskekatke.healthformula.data.models.user.UserResponse
import com.veskekatke.healthformula.data.repositories.Recommendation
import com.veskekatke.healthformula.presentation.view.states.PostsState
import com.veskekatke.healthformula.presentation.view.states.SupplementsState

interface MainContract {
    interface PostViewModel {
        val postsState: LiveData<PostsState>
        fun fetchAllPosts()
        fun getAllPosts()
        fun getAllOnPromotion()
        fun getAllRecommendedToMe(username : String)
        fun addRecommendation(recomm: Recommendation)
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
        val changedPassword: LiveData<ServerChangePasswordResponse>
        fun fetch(userId: String)
        fun get()
        fun getFoodItemsByName(filter : String)
        fun authenticate(credentials: Credentials)
        fun logOut()
        fun changePassword(passInfo: PasswordInformation)
        fun enableChangePassword()
        fun resetPassword(email: String)
    }
}