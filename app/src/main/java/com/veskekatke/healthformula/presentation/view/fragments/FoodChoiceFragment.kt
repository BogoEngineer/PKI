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
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.presentation.view.recycler.adapter.FoodItemAdapter
import com.veskekatke.healthformula.presentation.view.recycler.adapter.PostAdapter
import com.veskekatke.healthformula.presentation.view.recycler.diff.FoodItemDiffItemCallback
import com.veskekatke.healthformula.presentation.view.recycler.diff.PostDiffItemCallback
import com.veskekatke.healthformula.presentation.viewmodel.FoodItemViewModel
import com.veskekatke.healthformula.presentation.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.fragment_foodchoice.*
import kotlinx.android.synthetic.main.fragment_home.*

class FoodChoiceFragment : Fragment(R.layout.fragment_foodchoice){
    private val foodItemViewModel: FoodItemViewModel by viewModels()

    private lateinit var foodItemAllowedAdapter: FoodItemAdapter
    private lateinit var foodItemNotAllowedAdapter: FoodItemAdapter

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
        initRecycler()
    }

    private fun initRecycler(){
        allowedRv.layoutManager = LinearLayoutManager(this.context)
        foodItemAllowedAdapter = FoodItemAdapter(FoodItemDiffItemCallback()){

        }
        allowedRv.adapter = foodItemAllowedAdapter

        notAllowedRv.layoutManager = LinearLayoutManager(this.context)
        foodItemNotAllowedAdapter = FoodItemAdapter(FoodItemDiffItemCallback()){

        }
        notAllowedRv.adapter = foodItemNotAllowedAdapter
    }

    private fun initObservers(){
        foodItemViewModel.getAllowed().observe(viewLifecycleOwner, Observer {
            foodItemAllowedAdapter.submitList(it)
        })

        foodItemViewModel.getNotAllowed().observe(viewLifecycleOwner, Observer{
            foodItemNotAllowedAdapter.submitList(it)
        })
    }
}