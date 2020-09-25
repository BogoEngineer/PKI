package com.veskekatke.healthformula.modules

import com.veskekatke.healthformula.data.datasources.remote.user.UserService
import com.veskekatke.healthformula.data.repositories.UserRepository
import com.veskekatke.healthformula.data.repositories.UserRepositoryImpl
import com.veskekatke.healthformula.presentation.view.activities.MainActivity
import com.veskekatke.healthformula.presentation.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModule = module {

    viewModel { UserViewModel(userRepository = get()) }

    single<UserRepository> { UserRepositoryImpl(remoteDataSource = get()) }

    single<UserService> { create(retrofit = get()) }

}