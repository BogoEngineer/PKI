package com.veskekatke.healthformula.data.models.user

data class PasswordInformation(
    val email : String,
    val old_password : String,
    val new_password : String
)