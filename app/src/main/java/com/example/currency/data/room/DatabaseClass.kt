package com.example.currency.data.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.currency.data.models.currency.Currency

@Database(entities = [CurrencyExchange::class, Currency::class],version = 4)
abstract class DatabaseClass : RoomDatabase() {
    abstract val dao: Dao?
    companion object{
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `currency2` (`id` INTEGER, `key` TEXT, `value` TEXT" +
                        "PRIMARY KEY(`id`))")
            }
        }
    }
}
