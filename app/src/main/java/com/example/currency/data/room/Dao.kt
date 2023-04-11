package com.example.currency.data.room

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@androidx.room.Dao
interface Dao {
    @Insert
    fun AddExchange(orderItemList: CurrencyExchange?)

    @Query("select * from Currency")
    fun get_all_exchanges(): List<CurrencyExchange?>?

    @Query("delete from Currency")
    fun deleteAllproduct()


    @Update
    fun editproduct(basketModel: CurrencyExchange?)
}