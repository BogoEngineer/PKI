package com.veskekatke.healthformula.data.datasources.local.post

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.veskekatke.healthformula.data.models.post.PostEntity

@Database(
    entities = [PostEntity::class],
    version = 2,
    exportSchema = false)
@TypeConverters()
abstract class PostDatabase : RoomDatabase() {
    abstract fun getPostDao(): PostDao
}