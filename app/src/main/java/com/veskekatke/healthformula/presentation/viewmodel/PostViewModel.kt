package com.veskekatke.healthformula.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.veskekatke.healthformula.data.models.resource.Resource
import com.veskekatke.healthformula.data.repositories.PostRepository
import com.veskekatke.healthformula.data.repositories.Recommendation
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.view.states.PostsState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PostViewModel(
    private val postRepository : PostRepository
) : ViewModel(), MainContract.PostViewModel{

    private val subscriptions = CompositeDisposable()
    override val postsState: MutableLiveData<PostsState> = MutableLiveData()

    override fun fetchAllPosts() {
        val subscription = postRepository
            .fetchAll()
            .startWith(Resource.Loading()) // set state on loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> postsState.value = PostsState.Loading
                        is Resource.Success -> postsState.value = PostsState.DataFetched
                        is Resource.Error -> postsState.value = PostsState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    postsState.value = PostsState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllPosts() {
        val subscription = postRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    postsState.value = PostsState.Success(it)
                },
                {
                    postsState.value = PostsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllOnPromotion() {
        val subscription = postRepository
            .getAllOnPromotion()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    postsState.value = PostsState.Success(it)
                },
                {
                    postsState.value = PostsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllRecommendedToMe(username: String) {
        val subscription = postRepository
            .getAllRecommendedToMe(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    postsState.value = PostsState.Success(it)
                },
                {
                    postsState.value = PostsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun addRecommendation(recommendation: Recommendation){
        val subscription = postRepository
            .addRecommendation(recommendation)
    }
}