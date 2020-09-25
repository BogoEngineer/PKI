package com.veskekatke.healthformula.data.datasources.remote.supplement

import com.veskekatke.healthformula.data.datasources.ServerResponse
import com.veskekatke.healthformula.data.models.post.PostResponse
import com.veskekatke.healthformula.data.models.supplement.SupplementResponse
import io.reactivex.Observable
import retrofit2.http.GET
interface SupplementService {

    @GET("user/supplements")
    fun getAll(): Observable<ServerResponse<SupplementResponse>>

}