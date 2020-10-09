package com.veskekatke.healthformula.data.repositories

import android.content.SharedPreferences
import com.veskekatke.healthformula.data.datasources.local.supplement.SupplementDao
import com.veskekatke.healthformula.data.datasources.remote.supplement.SupplementService
import com.veskekatke.healthformula.data.models.resource.Resource
import com.veskekatke.healthformula.data.models.supplement.Supplement
import com.veskekatke.healthformula.data.models.supplement.SupplementEntity
import io.reactivex.Observable
import org.koin.core.Koin
import org.koin.core.KoinComponent
import org.koin.core.inject

class SupplementRepositoryImpl(
    private val localDataSource: SupplementDao,
    private val remoteDataSource: SupplementService
) : SupplementRepository {

    override fun fetchAll(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAll()
            .doOnNext {
                val entities = it.data.map {
                    SupplementEntity(
                        it._id,
                        it.name,
                        it.manufacturer,
                        it.description,
                        it.image
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAll(): Observable<List<Supplement>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Supplement(it.id, it.name, it.manufacturer, it.description, it.image)
                }
            }
    }

    override fun getAllByName(name: String): Observable<List<Supplement>> {
        return localDataSource
            .getByName(name)
            .map {
                it.map {
                    Supplement(it.id, it.name, it.manufacturer, it.description, it.image)
                }
            }
    }
}