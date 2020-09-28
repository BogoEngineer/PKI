package com.veskekatke.healthformula.data.models.phase

import com.veskekatke.healthformula.data.models.foodChoice.FoodChoiceResponse
import com.veskekatke.healthformula.data.models.mealPlan.MealPlanResponse
import com.veskekatke.healthformula.data.models.supplementPlan.SupplementPlanResponse

data class PhaseResponse(
    val _id: String,
    val exception: String,
    val phase_number: Int,
    val meal_plan: MealPlanResponse,
    var food_choice: FoodChoiceResponse,
    val supplement_plan: SupplementPlanResponse
)