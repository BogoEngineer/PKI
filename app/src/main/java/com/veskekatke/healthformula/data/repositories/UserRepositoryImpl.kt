package com.veskekatke.healthformula.data.repositories

import android.content.SharedPreferences
import com.google.gson.Gson
import com.veskekatke.healthformula.data.datasources.ServerUserResponse
import com.veskekatke.healthformula.data.datasources.remote.user.UserService
import com.veskekatke.healthformula.data.models.supplement.Supplement
import com.veskekatke.healthformula.data.models.user.UserResponse
import io.reactivex.Observable
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class UserRepositoryImpl(
    private val remoteDataSource: UserService
) : UserRepository, KoinComponent {

    private val gson = Gson()
    private val sharedPref : SharedPreferences by inject()
    //private val sharedPref = sp.getSharedPreferences(activity.getString(R.string.user_info_key), Context.MODE_PRIVATE)
    private val key =  "userInfo"

    override fun fetch(): Single<ServerUserResponse<UserResponse>> {
        return remoteDataSource
            .get()
            .doOnSuccess {
                //Timber.e("SUCCESS")
                with (sharedPref.edit()) {
                    putString(key, gson.toJson(it.data))
                    commit()
                }
            }
            .doOnError {
                Timber.e(it.toString())
            }

    }

    override fun get(): UserResponse {
        return gson.fromJson<UserResponse>(sharedPref.getString(key, ""), UserResponse::class.java)
    }

    /*override fun getFoodItemsByName(name: String) {
        var filteredUser = get()

        filteredUser.phase.food_choice.allowed = filteredUser.phase.food_choice.allowed.filter{
            name in it.name
        }

        filteredUser.phase.food_choice.not_allowed = filteredUser.phase.food_choice.not_allowed.filter{
            name in it.name
        }

        with (sharedPref.edit()) {
            putString(key, gson.toJson(filteredUser))
            commit()
        }
    }*/
}