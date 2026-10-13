package com.cleavy.cleavy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bills")
data class BillEntity(
    @PrimaryKey(autoGenerate = true)
    val bill_id: Long = 0,
    val bill_name: String,
    val bill_amount: Double,
    val bill_due_date: Long,
    val bill_is_recurring: Boolean,
    val bill_is_paid: Boolean = false
)