package com.veskekatke.healthformula.data.datasources

data class ServerUserResponse<T> (
    val status: Boolean,
    val data : T
)