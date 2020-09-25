package com.veskekatke.healthformula.modules


import com.veskekatke.healthformula.data.datasources.local.supplement.SupplementDao
import com.veskekatke.healthformula.data.datasources.local.supplement.SupplementDatabase
import com.veskekatke.healthformula.data.datasources.remote.supplement.SupplementService
import com.veskekatke.healthformula.data.repositories.SupplementRepository
import com.veskekatke.healthformula.data.repositories.SupplementRepositoryImpl
import com.veskekatke.healthformula.presentation.viewmodel.SupplementViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val supplementModule = module {

    viewModel { SupplementViewModel(supplementRepository = get()) }

    single<SupplementRepository> { SupplementRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<SupplementDatabase>().getSupplementDao() }

    single<SupplementService> { create(retrofit = get()) }

}