package com.veskekatke.healthformula.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.veskekatke.healthformula.data.models.resource.Resource
import com.veskekatke.healthformula.data.models.supplement.Supplement
import com.veskekatke.healthformula.data.repositories.SupplementRepository
import com.veskekatke.healthformula.data.repositories.SupplementRepositoryImpl
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.view.states.SupplementsState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SupplementViewModel(
    private val supplementRepository: SupplementRepository
) : ViewModel(), MainContract.SupplementViewModel{

    private val subscriptions = CompositeDisposable()
    override val supplementsState: MutableLiveData<SupplementsState> = MutableLiveData<SupplementsState>()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                supplementRepository
                    .getAllByName(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    supplementsState.value = SupplementsState.Success(it)
                },
                {
                    supplementsState.value = SupplementsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllSupplements() {
        val subscription = supplementRepository
            .fetchAll()
            .startWith(Resource.Loading()) // set state on loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> supplementsState.value = SupplementsState.Loading
                        is Resource.Success -> supplementsState.value = SupplementsState.DataFetched
                        is Resource.Error -> supplementsState.value = SupplementsState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    supplementsState.value = SupplementsState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllSupplements() {
        val subscription = supplementRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    supplementsState.value = SupplementsState.Success(it)
                },
                {
                    supplementsState.value = SupplementsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getSupplementsByName(name: String) {
        publishSubject.onNext(name)
    }

    fun getMySupplements() : LiveData<List<List<Supplement>>>{
        return MutableLiveData<List<List<Supplement>>>()
    }
}