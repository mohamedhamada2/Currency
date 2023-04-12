package com.example.currency.data.models.currency

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency2")
data class Currency(val key: String, val value: String){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
