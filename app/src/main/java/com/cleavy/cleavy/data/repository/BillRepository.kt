package com.cleavy.cleavy.data.repository

import com.cleavy.cleavy.data.local.dao.BillDao
import com.cleavy.cleavy.data.local.entity.BillEntity
import kotlinx.coroutines.flow.Flow

class BillRepository(
    private val billDao: BillDao
) {
    val bills: Flow<List<BillEntity>> = billDao.getAllBills()

    suspend fun insertBill(bill: BillEntity) {
        billDao.insertBill(bill)
    }
    suspend fun updateBill(bill: BillEntity) {
        billDao.updateBill(bill)
    }
    suspend fun deleteBill(bill: BillEntity) {
        billDao.deleteBill(bill)
    }
}