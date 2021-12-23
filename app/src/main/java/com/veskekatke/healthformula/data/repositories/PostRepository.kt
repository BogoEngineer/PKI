package com.veskekatke.healthformula.data.repositories

import com.veskekatke.healthformula.data.models.post.Post
import com.veskekatke.healthformula.data.models.resource.Resource
import io.reactivex.Observable

interface PostRepository {
    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Post>>
    fun getAllOnPromotion(): Observable<List<Post>>
    fun getAllRecommendedToMe(username : String): Observable<List<Post>>
    fun addRecommendation(recommendation: Recommendation)
}
