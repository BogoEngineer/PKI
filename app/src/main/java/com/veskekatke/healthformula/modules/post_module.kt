package com.veskekatke.healthformula.modules

import com.veskekatke.healthformula.data.datasources.local.post.PostDatabase
import com.veskekatke.healthformula.data.datasources.remote.post.PostService
import com.veskekatke.healthformula.data.repositories.PostRepository
import com.veskekatke.healthformula.data.repositories.PostRepositoryImpl
import com.veskekatke.healthformula.presentation.viewmodel.PostViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postModule = module {

    viewModel { PostViewModel(postRepository = get()) }

    single<PostRepository> { PostRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<PostDatabase>().getPostDao() }

    single<PostService> { create(retrofit = get()) }

}