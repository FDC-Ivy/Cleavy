package com.cleavy.cleavy.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cleavy.cleavy.data.local.dao.BillDao
import com.cleavy.cleavy.data.local.entity.BillEntity

@Database(
    entities = [BillEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CleavyDatabase : RoomDatabase() {
    abstract fun billDao(): BillDao
}