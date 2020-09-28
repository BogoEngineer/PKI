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
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit

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
                user.value = it.data
            }, {
                Timber.e(it.toString())
            })
        subscriptions.add(subscription)
    }

    override fun get() {
        user.value = userRepository.get()
    }

    override fun getFoodItemsByName(filter : String){
        var usr = user.value
        usr!!.phase.food_choice.allowed = usr!!.phase.food_choice.allowed.filter { it.name.toLowerCase().contains(filter) }
        usr!!.phase.food_choice.not_allowed = usr!!.phase.food_choice.not_allowed.filter { it.name.toLowerCase().contains(filter) }

        user.value = usr
    }
}
