package com.veskekatke.healthformula.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.veskekatke.healthformula.data.models.foodItem.FoodItemResponse

class FoodItemDiffItemCallback : DiffUtil.ItemCallback<FoodItemResponse>(){
    override fun areItemsTheSame(oldItem: FoodItemResponse, newItem: FoodItemResponse): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: FoodItemResponse, newItem: FoodItemResponse): Boolean {
        return true // food item cant be changed by user
    }

}