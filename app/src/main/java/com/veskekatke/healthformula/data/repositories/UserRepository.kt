package com.veskekatke.healthformula.data.repositories

import com.veskekatke.healthformula.data.datasources.ServerResponse
import com.veskekatke.healthformula.data.datasources.ServerUserResponse
import com.veskekatke.healthformula.data.models.resource.Resource
import com.veskekatke.healthformula.data.models.user.UserResponse
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {
    fun fetch(): Single<ServerUserResponse<UserResponse>>
    fun get(): UserResponse
}