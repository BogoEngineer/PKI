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
import com.veskekatke.healthformula.presentation.viewmodel.MealPlanViewModel
import kotlinx.android.synthetic.main.fragment_currentplan.*
import kotlinx.android.synthetic.main.fragment_home.*

class CurrentPlanFragment : Fragment(R.layout.fragment_currentplan){

    private val mealPlanViewModel : MealPlanViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<NavigationView>(R.id.navView)?.setCheckedItem(R.id.home)
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
        mealPlanViewModel.getMealPlan().observe(viewLifecycleOwner, Observer {
            titleTv.text = it.name
            //contentMpTv.text = it.description
        })
    }
}