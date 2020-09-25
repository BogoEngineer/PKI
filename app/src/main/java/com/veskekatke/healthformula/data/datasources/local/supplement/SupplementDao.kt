package com.veskekatke.healthformula.data.datasources.local.supplement

import androidx.room.*
import com.veskekatke.healthformula.data.models.supplement.SupplementEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class SupplementDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<SupplementEntity>): Completable

    @Query("SELECT * FROM supplements")
    abstract fun getAll(): Observable<List<SupplementEntity>>

    @Query("DELETE FROM supplements")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<SupplementEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

    @Query("SELECT * FROM supplements WHERE name LIKE '%' || :name || '%'")
    abstract fun getByName(name: String): Observable<List<SupplementEntity>>
}