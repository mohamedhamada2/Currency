package com.example.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.domain.entity.currency.Currency

@Database(entities = [CurrencyExchange::class, com.example.domain.entity.currency.Currency::class],version = 4)
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
