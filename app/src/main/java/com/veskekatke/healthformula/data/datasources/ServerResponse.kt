package com.veskekatke.healthformula.data.datasources

data class ServerResponse<T> (
    val status: Boolean,
    val data : List<T>
)