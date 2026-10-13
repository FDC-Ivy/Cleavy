package com.cleavy.cleavy.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.cleavy.cleavy.`data`.local.entity.BillEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class BillDao_Impl(
  __db: RoomDatabase,
) : BillDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfBillEntity: EntityInsertAdapter<BillEntity>

  private val __deleteAdapterOfBillEntity: EntityDeleteOrUpdateAdapter<BillEntity>

  private val __updateAdapterOfBillEntity: EntityDeleteOrUpdateAdapter<BillEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfBillEntity = object : EntityInsertAdapter<BillEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `bills` (`bill_id`,`bill_name`,`bill_amount`,`bill_due_date`,`bill_is_recurring`,`bill_is_paid`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: BillEntity) {
        statement.bindLong(1, entity.bill_id)
        statement.bindText(2, entity.bill_name)
        statement.bindDouble(3, entity.bill_amount)
        statement.bindLong(4, entity.bill_due_date)
        val _tmp: Int = if (entity.bill_is_recurring) 1 else 0
        statement.bindLong(5, _tmp.toLong())
        val _tmp_1: Int = if (entity.bill_is_paid) 1 else 0
        statement.bindLong(6, _tmp_1.toLong())
      }
    }
    this.__deleteAdapterOfBillEntity = object : EntityDeleteOrUpdateAdapter<BillEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `bills` WHERE `bill_id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: BillEntity) {
        statement.bindLong(1, entity.bill_id)
      }
    }
    this.__updateAdapterOfBillEntity = object : EntityDeleteOrUpdateAdapter<BillEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `bills` SET `bill_id` = ?,`bill_name` = ?,`bill_amount` = ?,`bill_due_date` = ?,`bill_is_recurring` = ?,`bill_is_paid` = ? WHERE `bill_id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: BillEntity) {
        statement.bindLong(1, entity.bill_id)
        statement.bindText(2, entity.bill_name)
        statement.bindDouble(3, entity.bill_amount)
        statement.bindLong(4, entity.bill_due_date)
        val _tmp: Int = if (entity.bill_is_recurring) 1 else 0
        statement.bindLong(5, _tmp.toLong())
        val _tmp_1: Int = if (entity.bill_is_paid) 1 else 0
        statement.bindLong(6, _tmp_1.toLong())
        statement.bindLong(7, entity.bill_id)
      }
    }
  }

  public override suspend fun insertBill(bill: BillEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfBillEntity.insert(_connection, bill)
  }

  public override suspend fun deleteBill(bill: BillEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfBillEntity.handle(_connection, bill)
  }

  public override suspend fun updateBill(bill: BillEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfBillEntity.handle(_connection, bill)
  }

  public override fun getAllBills(): Flow<List<BillEntity>> {
    val _sql: String = "SELECT * FROM bills ORDER BY bill_due_date ASC"
    return createFlow(__db, false, arrayOf("bills")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfBillId: Int = getColumnIndexOrThrow(_stmt, "bill_id")
        val _columnIndexOfBillName: Int = getColumnIndexOrThrow(_stmt, "bill_name")
        val _columnIndexOfBillAmount: Int = getColumnIndexOrThrow(_stmt, "bill_amount")
        val _columnIndexOfBillDueDate: Int = getColumnIndexOrThrow(_stmt, "bill_due_date")
        val _columnIndexOfBillIsRecurring: Int = getColumnIndexOrThrow(_stmt, "bill_is_recurring")
        val _columnIndexOfBillIsPaid: Int = getColumnIndexOrThrow(_stmt, "bill_is_paid")
        val _result: MutableList<BillEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: BillEntity
          val _tmpBill_id: Long
          _tmpBill_id = _stmt.getLong(_columnIndexOfBillId)
          val _tmpBill_name: String
          _tmpBill_name = _stmt.getText(_columnIndexOfBillName)
          val _tmpBill_amount: Double
          _tmpBill_amount = _stmt.getDouble(_columnIndexOfBillAmount)
          val _tmpBill_due_date: Long
          _tmpBill_due_date = _stmt.getLong(_columnIndexOfBillDueDate)
          val _tmpBill_is_recurring: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfBillIsRecurring).toInt()
          _tmpBill_is_recurring = _tmp != 0
          val _tmpBill_is_paid: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfBillIsPaid).toInt()
          _tmpBill_is_paid = _tmp_1 != 0
          _item = BillEntity(_tmpBill_id,_tmpBill_name,_tmpBill_amount,_tmpBill_due_date,_tmpBill_is_recurring,_tmpBill_is_paid)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
