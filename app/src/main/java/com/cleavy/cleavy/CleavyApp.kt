
package com.cleavy.cleavy

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cleavy.cleavy.data.local.entity.BillEntity
import com.cleavy.cleavy.ui.screens.bills.AddBillScreen
import com.cleavy.cleavy.ui.screens.bills.BillViewModel
import com.cleavy.cleavy.ui.screens.bills.BillViewModelFactory
import com.cleavy.cleavy.ui.screens.history.PaymentHistoryScreen
import com.cleavy.cleavy.ui.screens.home.HomeScreen

// ADDED: Simple navigation destinations for Cleavy.
private enum class CleavyScreen {
    HOME,
    BILL_FORM,
    PAYMENT_HISTORY
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CleavyApp() {
    val context = LocalContext.current

    val appContainer =
        (context.applicationContext as CleavyApplication).appContainer

    val billViewModel: BillViewModel = viewModel(
        factory = BillViewModelFactory(
            appContainer.billRepository
        )
    )

    var currentScreen by remember {
        mutableStateOf(CleavyScreen.HOME)
    }

    var billToEdit by remember {
        mutableStateOf<BillEntity?>(null)
    }

    when (currentScreen) {
        CleavyScreen.HOME -> {
            HomeScreen(
                billViewModel = billViewModel,
                onAddBillClick = {
                    billToEdit = null
                    currentScreen = CleavyScreen.BILL_FORM
                },
                onEditBillClick = { bill ->
                    billToEdit = bill
                    currentScreen = CleavyScreen.BILL_FORM
                },

                onPaymentHistoryClick = {
                    currentScreen = CleavyScreen.PAYMENT_HISTORY
                }
            )
        }

        CleavyScreen.BILL_FORM -> {
            AddBillScreen(
                billViewModel = billViewModel,
                billToEdit = billToEdit,
                onCancel = {
                    billToEdit = null
                    currentScreen = CleavyScreen.HOME
                },
                onSaved = {
                    billToEdit = null
                    currentScreen = CleavyScreen.HOME
                }
            )
        }

        CleavyScreen.PAYMENT_HISTORY -> {
            PaymentHistoryScreen(
                billViewModel = billViewModel,
                onBack = {
                    currentScreen = CleavyScreen.HOME
                }
            )
        }
    }
}
