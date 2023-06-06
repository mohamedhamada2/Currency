package com.example.domain.usecase.currency

import com.example.domain.entity.convert.ConvertModel
import com.example.domain.entity.currency.Currency
import com.example.domain.entity.currency.CurrencyModel
import com.example.domain.repo.currency.CurrencyRepository
import io.reactivex.rxjava3.core.Single

class GetCurrency(private val currencyRepository: CurrencyRepository) {
    fun get_currency() = currencyRepository.get_currency()
    fun convert_currency(currencyFromKey: String, currencyToKey: String, amount: Double) = currencyRepository.convert_currency(currencyFromKey, currencyToKey, amount)
    fun get_currency_list_from_local_db() = currencyRepository.get_currency_list_from_local_db()
    fun get_currency_list_from_api(currencymodel: CurrencyModel?) = currencyRepository.get_currency_list_from_api(currencymodel)
    fun get_currency_value_list_from_local_db() = currencyRepository.get_currency_value_list_from_local_db()
    fun get_currency_value_list_from_api() = currencyRepository.get_currency_value_list_from_api()
    fun save_currency_converter(from: String, to: String, rate: String, amount: String, result: String, date: String) = currencyRepository.save_currency_converter(from,to,rate,amount,result,date)

}