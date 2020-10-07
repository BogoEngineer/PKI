package com.veskekatke.healthformula.data.datasources

data class ServerAuthenticateResponse(
    val success: Boolean,
    val token : String?,
    val userId : String?,
    val msg : String?
)