package com.veskekatke.healthformula.data.repositories

import android.content.SharedPreferences
import com.veskekatke.healthformula.data.datasources.local.post.PostDao
import com.veskekatke.healthformula.data.datasources.remote.post.PostService
import com.veskekatke.healthformula.data.models.post.Post
import com.veskekatke.healthformula.data.models.post.PostEntity
import com.veskekatke.healthformula.data.models.resource.Resource
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class PostRepositoryImpl(
    private val localDataSource: PostDao,
    private val remoteDataSource: PostService
) : PostRepository {

    override fun fetchAll(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAll()
            .doOnNext {
                val entities = it.data.map {
                    PostEntity(
                        it._id,
                        it.name,
                        it.image,
                        it.content
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAll(): Observable<List<Post>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Post(it.id, it.name, it.image, it.content)
                }
            }
    }
}