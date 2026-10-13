package com.cleavy.cleavy.`data`.local.database

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.cleavy.cleavy.`data`.local.dao.BillDao
import com.cleavy.cleavy.`data`.local.dao.BillDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class CleavyDatabase_Impl : CleavyDatabase() {
  private val _billDao: Lazy<BillDao> = lazy {
    BillDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "bc284b364ff1da952bc653cff15594cf", "a5198c1ba425445bb4e31196f13a01a5") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `bills` (`bill_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `bill_name` TEXT NOT NULL, `bill_amount` REAL NOT NULL, `bill_due_date` INTEGER NOT NULL, `bill_is_recurring` INTEGER NOT NULL, `bill_is_paid` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'bc284b364ff1da952bc653cff15594cf')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `bills`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsBills: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsBills.put("bill_id", TableInfo.Column("bill_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBills.put("bill_name", TableInfo.Column("bill_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBills.put("bill_amount", TableInfo.Column("bill_amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBills.put("bill_due_date", TableInfo.Column("bill_due_date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBills.put("bill_is_recurring", TableInfo.Column("bill_is_recurring", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBills.put("bill_is_paid", TableInfo.Column("bill_is_paid", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysBills: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesBills: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoBills: TableInfo = TableInfo("bills", _columnsBills, _foreignKeysBills, _indicesBills)
        val _existingBills: TableInfo = read(connection, "bills")
        if (!_infoBills.equals(_existingBills)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |bills(com.cleavy.cleavy.data.local.entity.BillEntity).
              | Expected:
              |""".trimMargin() + _infoBills + """
              |
              | Found:
              |""".trimMargin() + _existingBills)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "bills")
  }

  public override fun clearAllTables() {
    super.performClear(false, "bills")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(BillDao::class, BillDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun billDao(): BillDao = _billDao.value
}
