package com.veskekatke.healthformula.presentation.viewmodel
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.veskekatke.healthformula.data.models.resource.Resource
import com.veskekatke.healthformula.data.models.user.UserResponse
import com.veskekatke.healthformula.data.repositories.UserRepository
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.view.states.SupplementsState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel(), MainContract.UserViewModel {

    private val subscriptions = CompositeDisposable()
    override val user: MutableLiveData<UserResponse> = MutableLiveData<UserResponse>()

    override fun fetch() {
        val subscription = userRepository
            .fetch()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //Timber.e("DOHVACENO: " + it.toString())
                user.value = it.data
                Timber.e("dohv: "+it.data.toString())
            }, {
                Timber.e(it.toString())
            })
        subscriptions.add(subscription)
    }

    override fun get() {
        user.value = userRepository.get()
        //Timber.e("DOHVACENO: " + user.value.toString())
    }
}
