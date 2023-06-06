package com.example.data.local.room

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.domain.entity.currency.Currency

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

    @Insert
    fun AddCurrencySymbols(currency: com.example.domain.entity.currency.Currency?)
    @Query("select * from currency2")
    fun get_all_currency_symbols(): List<com.example.domain.entity.currency.Currency>?
}