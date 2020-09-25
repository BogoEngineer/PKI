package com.veskekatke.healthformula.data.models.foodChoice

import com.veskekatke.healthformula.data.models.foodItem.FoodItemResponse

data class FoodChoiceResponse(
    val _id : String,
    val name : String,
    val allowed : List<FoodItemResponse>,
    val not_allowed : List<FoodItemResponse>
)