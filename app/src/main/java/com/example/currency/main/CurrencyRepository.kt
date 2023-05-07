package com.example.currency.main

import androidx.lifecycle.MutableLiveData
import com.example.currency.api.Api
import com.example.currency.data.models.convert.ConvertModel
import com.example.currency.data.models.currency.CurrencyModel
import com.example.currency.data.room.DatabaseClass
import io.reactivex.rxjava3.core.Single

interface CurrencyRepository {

    fun get_currency(): Single<CurrencyModel>
    fun convert_currency(currencyFromKey: String, currencyToKey: String, amount: Double):Single<ConvertModel>

}