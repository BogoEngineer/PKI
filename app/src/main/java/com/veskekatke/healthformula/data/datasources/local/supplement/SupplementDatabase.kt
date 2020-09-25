package com.veskekatke.healthformula.data.datasources.local.supplement

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.veskekatke.healthformula.data.models.supplement.SupplementEntity

@Database(
    entities = [SupplementEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters()
abstract class SupplementDatabase : RoomDatabase() {
    abstract fun getSupplementDao(): SupplementDao
}