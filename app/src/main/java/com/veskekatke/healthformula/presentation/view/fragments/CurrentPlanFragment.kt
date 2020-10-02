package com.veskekatke.healthformula.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.viewmodel.MealPlanViewModel
import com.veskekatke.healthformula.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_currentplan.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.compat.SharedViewModelCompat.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CurrentPlanFragment : Fragment(R.layout.fragment_currentplan){

    private val userViewModel : MainContract.UserViewModel by sharedViewModel<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initUI()
        initObservers()
    }

    private fun initUI(){
        Picasso
            .get()
            .load(R.drawable.meal_plan_background)
            .into(mealPlanIv)
    }

    private fun initObservers(){
        userViewModel.user.observe(viewLifecycleOwner, Observer {
            titleTv.text = it.phase?.meal_plan?.name
            contentMpTv.text = it.phase?.meal_plan?.description
        })
        userViewModel.get()
    }
}