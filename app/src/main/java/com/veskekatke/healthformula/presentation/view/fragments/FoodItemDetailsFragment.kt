package com.veskekatke.healthformula.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.foodItem.FoodItemResponse
import kotlinx.android.synthetic.main.fragment_foodchoice.*
import kotlinx.android.synthetic.main.fragment_fooditemdetails.*
import kotlinx.android.synthetic.main.fragment_supplementdetails.*

class FoodItemDetailsFragment(foodItem : FoodItemResponse? = null) :  Fragment(R.layout.fragment_fooditemdetails){
    var foodItem = foodItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        foodItem?.let { updateFoodItem(it) }
        initListeners()
    }

    private fun initListeners(){
        closeFoodItemB.setOnClickListener {
            (parentFragment as FoodChoiceFragment).closeDetails()
        }
    }

    fun updateFoodItem(fi : FoodItemResponse){
        foodItem = fi
        Picasso
            .get()
            .load(foodItem!!.image)
            .into(foodItemIv)

        contentFoodItemTv.text = foodItem!!.description
        titleFoodItemTv.text = foodItem!!.name
    }
}