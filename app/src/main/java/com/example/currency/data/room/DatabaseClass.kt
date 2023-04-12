package com.example.currency.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currency.data.models.currency.Currency

@Database(entities = [CurrencyExchange::class, Currency::class], version = 2, exportSchema = false)
abstract class DatabaseClass : RoomDatabase() {
    abstract val dao: Dao?
}