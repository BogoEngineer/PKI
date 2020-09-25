package com.veskekatke.healthformula.data.models.supplementPlan

import com.veskekatke.healthformula.data.models.supplement.SupplementResponse

data class SupplementPlanResponse(
    val before_breakfast: List<SupplementResponse>,
    val after_breakfast: List<SupplementResponse>,
    val am_snack: List<SupplementResponse>,
    val before_lunch: List<SupplementResponse>,
    val after_lunch: List<SupplementResponse>,
    val pm_snack: List<SupplementResponse>,
    val before_dinner: List<SupplementResponse>,
    val after_dinner: List<SupplementResponse>
)