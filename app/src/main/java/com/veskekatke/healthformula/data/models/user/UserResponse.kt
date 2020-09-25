package com.veskekatke.healthformula.data.models.user

import com.veskekatke.healthformula.data.models.phase.PhaseResponse

data class UserResponse(
    val _id: String,
    val admin: Boolean,
    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val image: String,
    val content: String,
    val phase: PhaseResponse,
    val date_created: String
)