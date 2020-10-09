package com.veskekatke.healthformula.data.datasources.remote.user

import com.veskekatke.healthformula.data.datasources.ServerAuthenticateResponse
import com.veskekatke.healthformula.data.datasources.ServerChangePasswordResponse
import com.veskekatke.healthformula.data.datasources.ServerResponse
import com.veskekatke.healthformula.data.datasources.ServerUserResponse
import com.veskekatke.healthformula.data.models.user.Credentials
import com.veskekatke.healthformula.data.models.user.PasswordInformation
import com.veskekatke.healthformula.data.models.user.UserResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface UserService {

    @GET("user/info/{user_id}")
    fun get(@Path(value = "user_id", encoded = true) userId:String, @Header("Authorization") jwt: String): Single<ServerUserResponse<UserResponse>>

    @POST("user/authenticate")
    fun authenticate(@Body body: Credentials): Single<ServerAuthenticateResponse>

    @POST("user/changePassword")
    fun changePassword(@Body body: PasswordInformation, @Header("Authorization") jwt: String): Single<ServerChangePasswordResponse>

    @GET("user/forgotPassword/{email}")
    fun resetPassword(@Path(value = "email", encoded = true) email:String): Single<ServerUserResponse<UserResponse>>
}