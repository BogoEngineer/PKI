package com.veskekatke.healthformula.data.datasources.remote.post

import com.veskekatke.healthformula.data.datasources.ServerResponse
import com.veskekatke.healthformula.data.models.post.PostResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header

interface PostService {

    @GET("user/posts")
    fun getAll(): Observable<ServerResponse<PostResponse>>

}