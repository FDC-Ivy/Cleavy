package com.cleavy.cleavy.ui.screens.bills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleavy.cleavy.data.local.entity.BillEntity
import com.cleavy.cleavy.data.repository.BillRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BillViewModel(
    private val repository: BillRepository
) : ViewModel() {
    val bills : StateFlow<List<BillEntity>> = repository.bills.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun addBill(bill: BillEntity) {
        viewModelScope.launch {
            repository.insertBill(bill)
        }
    }

    fun updateBill(bill: BillEntity) {
        viewModelScope.launch {
            repository.updateBill(bill)
        }
    }

    fun deleteBill(bill: BillEntity) {
        viewModelScope.launch {
            repository.deleteBill(bill)
        }
    }
}