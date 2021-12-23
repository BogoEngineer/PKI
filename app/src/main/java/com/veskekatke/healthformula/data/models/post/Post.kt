package com.veskekatke.healthformula.data.models.post

import com.google.gson.annotations.SerializedName

data class Post (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("picture") val picture: String,
    @SerializedName("content") val content: String,
    @SerializedName("author") val author: String,
    @SerializedName("year") val year: String,
    @SerializedName("pages") val pages: Int,
    @SerializedName("promotion") val promotion: Boolean,
    @SerializedName("comments") val comments: List<Comment>?
)

data class Comment (
    val text: String,
    val rating: Int
    )

data class User(
    val username : String,
    val password : String,
    val firstName : String,
    val lastName : String,
    val phone : String,
    val address : String
)