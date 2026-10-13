package com.cleavy.cleavy.ui.screens.bills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cleavy.cleavy.data.repository.BillRepository

class BillViewModelFactory(
    private val repository: BillRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BillViewModel::class.java)) {
            return BillViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}