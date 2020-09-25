package com.veskekatke.healthformula.data.models.post

import com.google.gson.annotations.SerializedName

data class Post (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("picture") val picture: String,
    @SerializedName("content") val content: String
)