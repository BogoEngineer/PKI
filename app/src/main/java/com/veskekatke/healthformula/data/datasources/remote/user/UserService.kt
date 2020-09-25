package com.veskekatke.healthformula.data.datasources.remote.user

import com.veskekatke.healthformula.data.datasources.ServerResponse
import com.veskekatke.healthformula.data.datasources.ServerUserResponse
import com.veskekatke.healthformula.data.models.user.UserResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface UserService {

    @GET("user/info/5f23eaada991284890a1cffb")
    fun get(): Single<ServerUserResponse<UserResponse>>
}