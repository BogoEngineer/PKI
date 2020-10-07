package com.veskekatke.healthformula.data.datasources.remote.user

import com.veskekatke.healthformula.data.datasources.ServerAuthenticateResponse
import com.veskekatke.healthformula.data.datasources.ServerResponse
import com.veskekatke.healthformula.data.datasources.ServerUserResponse
import com.veskekatke.healthformula.data.models.user.Credentials
import com.veskekatke.healthformula.data.models.user.UserResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface UserService {

    @GET("user/info/{user_id}")
    fun get(@Path(value = "user_id", encoded = true) userId:String, @Header("Authorization") jwt: String): Single<ServerUserResponse<UserResponse>>

    @POST("user/authenticate")
    fun authenticate(@Body body: Credentials): Single<ServerAuthenticateResponse>
}