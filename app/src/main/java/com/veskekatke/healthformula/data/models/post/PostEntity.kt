package com.veskekatke.healthformula.data.models.post

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val name: String,
    val image: String,
    val content: String
)