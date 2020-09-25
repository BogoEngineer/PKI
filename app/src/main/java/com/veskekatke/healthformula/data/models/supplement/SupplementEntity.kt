package com.veskekatke.healthformula.data.models.supplement

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supplements")
data class SupplementEntity(
    @PrimaryKey val id: String,
    val name: String,
    val manufacturer: String,
    val description: String,
    val image: String
)