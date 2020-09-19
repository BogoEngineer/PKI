package com.veskekatke.healthformula.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.data.models.foodItem.FoodItem
import com.veskekatke.healthformula.presentation.view.recycler.diff.FoodItemDiffItemCallback
import com.veskekatke.healthformula.presentation.view.recycler.viewholder.FoodItemViewHolder

class FoodItemAdapter (
    foodDiffItemCallback: FoodItemDiffItemCallback,
    private val onPostClicked: (FoodItem) -> Unit) : ListAdapter<FoodItem, FoodItemViewHolder>(foodDiffItemCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.layout_food_list_item, parent, false)
        return FoodItemViewHolder(containerView) {
            val foodItem = getItem(it)
            onPostClicked.invoke(foodItem)
        }
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        val foodItem = getItem(position)
        holder.bind(foodItem)
    }


}