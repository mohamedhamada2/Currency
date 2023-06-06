package com.example.domain.repo.currency

import com.example.domain.entity.convert.ConvertModel
import com.example.domain.entity.currency.Currency
import com.example.domain.entity.currency.CurrencyModel

import io.reactivex.rxjava3.core.Single

interface CurrencyRepository {

    fun get_currency(): Single<CurrencyModel>
    fun convert_currency(currencyFromKey: String, currencyToKey: String, amount: Double):Single<ConvertModel>
    fun get_currency_list_from_local_db():ArrayList<Currency>

    fun get_currency_list_from_api(currencymodel: CurrencyModel?):ArrayList<Currency>

    fun get_currency_value_list_from_local_db():ArrayList<String>

    fun get_currency_value_list_from_api():ArrayList<String>
    fun save_currency_converter(from: String, to: String, rate: String, amount: String, result: String, date: String)

}