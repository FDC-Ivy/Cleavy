package com.cleavy.cleavy.ui.screens.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cleavy.cleavy.data.local.entity.BillEntity
import com.cleavy.cleavy.ui.screens.bills.BillViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    billViewModel: BillViewModel,
    onAddBillClick: () -> Unit,
    onEditBillClick: (BillEntity) -> Unit,
    onPaymentHistoryClick: () -> Unit
) {
    val bills by billViewModel.bills.collectAsState()
    var billToDelete by remember { mutableStateOf<BillEntity?>(null) }

    val today = LocalDate.now()
    val unpaidBills = bills.filter { !it.bill_is_paid }
    val moneyToPrepare = unpaidBills.sumOf { it.bill_amount }

    val moneyOverdue = unpaidBills
        .filter { it.bill_due_date.toLocalDate() < today }
        .sumOf { it.bill_amount }

    val moneyRemaining = moneyToPrepare - moneyOverdue

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Cleavy",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Know what's coming. Be ready.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SummaryCard(
                title = "Bills to Prepare",
                amount = moneyToPrepare,
                modifier = Modifier.weight(1f)
            )

            SummaryCard(
                title = "Overdue Bills",
                amount = moneyOverdue,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        SummaryCard(
            title = "Not Yet Overdue",
            amount = moneyRemaining,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Upcoming Bills (${unpaidBills.size})",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (unpaidBills.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "You're all caught up!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Add a bill to start preparing for upcoming payments.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    items = unpaidBills.sortedBy { it.bill_due_date },
                    key = { it.bill_id }
                ) { bill ->
                    BillItemCard(
                        bill = bill,
                        today = today,
                        onMarkPaid = {
                            billViewModel.updateBill(
                                bill.copy(bill_is_paid = true)
                            )
                        },
                        onDelete = {
                            billToDelete = bill
                        },
                        onEdit = {
                            onEditBillClick(bill)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onPaymentHistoryClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Payment History")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onAddBillClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("＋ Add Bill")
        }
    }

    billToDelete?.let { bill ->
        AlertDialog(
            onDismissRequest = { billToDelete = null },
            title = { Text("Delete bill?") },
            text = {
                Text("Are you sure you want to delete ${bill.bill_name}? This cannot be undone.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        billViewModel.deleteBill(bill)
                        billToDelete = null
                    }
                ) {
                    Text(
                        text = "Delete",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { billToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun SummaryCard(
    title: String,
    amount: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = formatPeso(amount),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun BillItemCard(
    bill: BillEntity,
    today: LocalDate,
    onMarkPaid: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    val dueDate = bill.bill_due_date.toLocalDate()
    val daysUntilDue = ChronoUnit.DAYS.between(today, dueDate)

    val (status, statusColor) = when {
        daysUntilDue < 0L ->
            "Overdue" to MaterialTheme.colorScheme.error

        daysUntilDue == 0L ->
            "Due today" to MaterialTheme.colorScheme.error

        daysUntilDue <= 3L ->
            "Due soon" to MaterialTheme.colorScheme.tertiary

        else ->
            "Upcoming" to MaterialTheme.colorScheme.primary
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = bill.bill_name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Due ${dueDate.format(
                            DateTimeFormatter.ofPattern(
                                "MMM d, yyyy",
                                Locale.getDefault()
                            )
                        )}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = statusColor.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = status,
                            modifier = Modifier.padding(
                                horizontal = 8.dp,
                                vertical = 4.dp
                            ),
                            style = MaterialTheme.typography.labelMedium,
                            color = statusColor
                        )
                    }

                    if (bill.bill_is_recurring) {
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "↻ Recurring",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Text(
                    text = formatPeso(bill.bill_amount),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onMarkPaid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("✓ Mark as Paid")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onEdit,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Edit")
                }

                OutlinedButton(
                    onClick = onDelete,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Delete")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.of("UTC"))
        .toLocalDate()
}

private fun formatPeso(amount: Double): String {
    return "₱" + String.format(Locale.US, "%,.2f", amount)
}