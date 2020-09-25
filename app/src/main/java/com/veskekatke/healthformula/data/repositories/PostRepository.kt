package com.veskekatke.healthformula.data.repositories

import com.veskekatke.healthformula.data.models.post.Post
import com.veskekatke.healthformula.data.models.resource.Resource
import io.reactivex.Observable

interface PostRepository {
    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Post>>
}
