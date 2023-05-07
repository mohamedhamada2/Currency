package com.example.currency.data.models.currency

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "Currency2")
data class CurrencyModel(val success: Boolean,
                         @TypeConverters(MapTypeConverter::class)
                         var symbols: HashMap<String, String> ? = null,
                         val error: Error){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}