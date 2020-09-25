package com.veskekatke.healthformula.data.models.supplement

data class Supplement(
    val id : String,
    val name : String,
    val manufacturer : String,
    val description : String,
    val picture : String // not added yet in database
)