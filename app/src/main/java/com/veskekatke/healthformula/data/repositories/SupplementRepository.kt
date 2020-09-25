package com.veskekatke.healthformula.data.repositories

import com.veskekatke.healthformula.data.models.resource.Resource
import com.veskekatke.healthformula.data.models.supplement.Supplement
import io.reactivex.Observable

interface SupplementRepository {
    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Supplement>>
    fun getAllByName(name: String): Observable<List<Supplement>>
}