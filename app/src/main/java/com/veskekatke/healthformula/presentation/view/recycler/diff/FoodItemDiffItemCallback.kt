package com.veskekatke.healthformula.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.veskekatke.healthformula.data.models.foodItem.FoodItem

class FoodItemDiffItemCallback : DiffUtil.ItemCallback<FoodItem>(){
    override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
        return true // food item cant be changed by user
    }

}