
package com.cleavy.cleavy.ui.screens.bills

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cleavy.cleavy.data.local.entity.BillEntity
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBillScreen(
    billViewModel: BillViewModel,
    billToEdit: BillEntity? = null,
    onCancel: () -> Unit = {},
    onSaved: () -> Unit = {}
) {
    var billName by remember(billToEdit?.bill_id) {
        mutableStateOf(billToEdit?.bill_name ?: "")
    }

    var billAmount by remember(billToEdit?.bill_id) {
        mutableStateOf(
            billToEdit?.bill_amount?.let {
                String.format(Locale.US, "%.2f", it)
            } ?: ""
        )
    }

    var dueDateMillis by remember(billToEdit?.bill_id) {
        mutableStateOf(billToEdit?.bill_due_date)
    }

    var isRecurring by remember(billToEdit?.bill_id) {
        mutableStateOf(billToEdit?.bill_is_recurring ?: false)
    }

    var showDatePicker by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val selectedDateText = dueDateMillis?.let { millis ->
        Instant.ofEpochMilli(millis)
            .atZone(ZoneOffset.UTC)
            .toLocalDate()
            .format(
                DateTimeFormatter.ofPattern(
                    "MMM d, yyyy",
                    Locale.getDefault()
                )
            )
    } ?: "Select payment due date"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextButton(onClick = onCancel) {
            Text("← Back")
        }

        Text(
            text = if (billToEdit == null) "Add a bill" else "Edit bill",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = if (billToEdit == null) {
                "Keep your next payment prepared."
            } else {
                "Update your bill details."
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = billName,
            onValueChange = {
                billName = it
                errorMessage = null
            },
            label = { Text("Bill name") },
            placeholder = { Text("e.g. VECO Electricity") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = billAmount,
            onValueChange = { input ->
                billAmount = input
                    .filter { it.isDigit() || it == '.' }
                    .let { value ->
                        val decimalIndex = value.indexOf('.')

                        if (decimalIndex == -1) {
                            value
                        } else {
                            value.substring(0, decimalIndex + 1) +
                                    value.substring(decimalIndex + 1)
                                        .replace(".", "")
                                        .take(2)
                        }
                    }

                errorMessage = null
            },
            label = { Text("Amount (PHP)") },
            placeholder = { Text("0.00") },
            prefix = { Text("₱ ") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Due date",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = selectedDateText,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    TextButton(
                        onClick = { showDatePicker = true }
                    ) {
                        Text("Choose")
                    }
                }
            )

            Text(
                text = "Choose the date your payment is due.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Recurring bill",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "For bills that repeat every month",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Switch(
                checked = isRecurring,
                onCheckedChange = { isRecurring = it }
            )
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val amount = billAmount.toDoubleOrNull()

                errorMessage = when {
                    billName.isBlank() ->
                        "Please enter a bill name."

                    amount == null ||
                            !amount.isFinite() ||
                            amount <= 0.0 ->
                        "Enter an amount greater than zero."

                    dueDateMillis == null ->
                        "Please select a due date."

                    else -> null
                }

                if (errorMessage == null) {
                    val bill = BillEntity(
                        bill_id = billToEdit?.bill_id ?: 0L,
                        bill_name = billName.trim(),
                        bill_amount = amount!!,
                        bill_due_date = dueDateMillis!!,
                        bill_is_recurring = isRecurring,
                        bill_is_paid = billToEdit?.bill_is_paid ?: false
                    )

                    if (billToEdit == null) {
                        billViewModel.addBill(bill)
                    } else {
                        billViewModel.updateBill(bill)
                    }

                    onSaved()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = if (billToEdit == null) {
                    "Save Bill"
                } else {
                    "Save Changes"
                }
            )
        }

        TextButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = dueDateMillis
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        dueDateMillis = datePickerState.selectedDateMillis
                        showDatePicker = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
