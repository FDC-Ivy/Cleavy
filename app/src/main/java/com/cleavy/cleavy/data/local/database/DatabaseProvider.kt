package com.cleavy.cleavy.data.local.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    fun getDatabase(context: Context): CleavyDatabase {
        return Room.databaseBuilder(
            context,
            CleavyDatabase::class.java,
            "cleavy_database"
        ).build()
    }
}