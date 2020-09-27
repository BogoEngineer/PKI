package com.veskekatke.healthformula.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.presentation.contract.MainContract
import com.veskekatke.healthformula.presentation.view.recycler.adapter.FoodItemAdapter
import com.veskekatke.healthformula.presentation.view.recycler.adapter.PostAdapter
import com.veskekatke.healthformula.presentation.view.recycler.diff.FoodItemDiffItemCallback
import com.veskekatke.healthformula.presentation.view.recycler.diff.PostDiffItemCallback
import com.veskekatke.healthformula.presentation.viewmodel.FoodItemViewModel
import com.veskekatke.healthformula.presentation.viewmodel.PostViewModel
import com.veskekatke.healthformula.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_foodchoice.*
import kotlinx.android.synthetic.main.fragment_fooditemdetails.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FoodChoiceFragment : Fragment(R.layout.fragment_foodchoice){
    private val userViewModel: MainContract.UserViewModel by sharedViewModel<UserViewModel>()

    private lateinit var bottomSheet : BottomSheetBehavior<View>

    private lateinit var foodItemAllowedAdapter: FoodItemAdapter
    private lateinit var foodItemNotAllowedAdapter: FoodItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initUI()
        initObservers()
    }

    fun closeDetails(){
        foodItemDetails.visibility = View.GONE
    }

    private fun initUI(){
        childFragmentManager.beginTransaction().add(R.id.foodItemDetails, FoodItemDetailsFragment()).commit()
        foodItemDetails.visibility = View.GONE
        foodItemDetails.bringToFront()
        initRecycler()
    }

    private fun initRecycler(){
        allowedRv.layoutManager = LinearLayoutManager(this.context)
        foodItemAllowedAdapter = FoodItemAdapter(FoodItemDiffItemCallback()){
            (childFragmentManager.findFragmentById(R.id.foodItemDetails) as FoodItemDetailsFragment)
                .updateFoodItem(it)
            foodItemDetails.visibility = View.VISIBLE
            lists.isFocusable = false
        }
        allowedRv.adapter = foodItemAllowedAdapter

        notAllowedRv.layoutManager = LinearLayoutManager(this.context)
        foodItemNotAllowedAdapter = FoodItemAdapter(FoodItemDiffItemCallback()){
            (childFragmentManager.findFragmentById(R.id.foodItemDetails) as FoodItemDetailsFragment)
                .updateFoodItem(it)
            foodItemDetails.visibility = View.VISIBLE
            lists.isFocusable = false
        }
        notAllowedRv.adapter = foodItemNotAllowedAdapter
    }

    private fun initObservers(){
        userViewModel.user.observe(viewLifecycleOwner, Observer {
            foodItemAllowedAdapter.submitList(it.phase.food_choice.allowed)
            foodItemNotAllowedAdapter.submitList(it.phase.food_choice.not_allowed)
        })
        userViewModel.get()
    }
}