package com.example.currency.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Currency")
data class CurrencyExchange(var from_currency:String,var to_currency:String,var base:String,var amount:String,var total:String,var date:String){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}