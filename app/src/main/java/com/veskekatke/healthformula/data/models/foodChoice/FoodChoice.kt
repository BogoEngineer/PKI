package com.veskekatke.healthformula.data.models.foodChoice

import com.veskekatke.healthformula.data.models.foodItem.FoodItem

data class FoodChoice(
    val id : Int,
    val name : String,
    val allowed : List<FoodItem>,
    val not_allowed : List<FoodItem>
)