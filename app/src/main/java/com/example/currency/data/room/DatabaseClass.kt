package com.example.currency.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyExchange::class], version = 1, exportSchema = false)
abstract class DatabaseClass : RoomDatabase() {
    abstract val dao: Dao?
}