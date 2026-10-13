package com.cleavy.cleavy.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cleavy.cleavy.data.local.entity.BillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {

    @Insert
    suspend fun insertBill(bill: BillEntity)
    @Update
    suspend fun updateBill(bill: BillEntity)
    @Delete
    suspend fun deleteBill(bill: BillEntity)
    @Query("SELECT * FROM bills ORDER BY bill_due_date ASC")
    fun getAllBills(): Flow<List<BillEntity>>
}