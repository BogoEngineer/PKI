package com.veskekatke.healthformula.data.datasources.local.post

import androidx.room.*
import com.veskekatke.healthformula.data.models.post.PostEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class PostDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<PostEntity>): Completable

    @Query("SELECT * FROM posts")
    abstract fun getAll(): Observable<List<PostEntity>>

    @Query("DELETE FROM posts")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<PostEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
}