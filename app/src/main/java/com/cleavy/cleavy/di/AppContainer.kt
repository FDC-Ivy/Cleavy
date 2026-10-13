package com.cleavy.cleavy.di

import android.content.Context
import com.cleavy.cleavy.data.local.database.DatabaseProvider
import com.cleavy.cleavy.data.repository.BillRepository

class AppContainer(context: Context) {
    private val database = DatabaseProvider.getDatabase(context)

    val billRepository = BillRepository(database.billDao())
}