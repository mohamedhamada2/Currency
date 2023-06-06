package com.example.data.repo.currency

import com.example.data.constants.Constants
import com.example.data.local.room.DatabaseClass
import com.example.data.local.sqlite.DBHelper
import com.example.data.remote.Api
import com.example.domain.repo.currency.CurrencyRepository
import com.example.domain.entity.convert.ConvertModel
import com.example.domain.entity.currency.Currency
import com.example.domain.entity.currency.CurrencyModel
import io.reactivex.rxjava3.core.Single

class CurrencyRepositoryImp(var currency_api: Api, var databaseClass: DatabaseClass, var dbHelper: DBHelper):
    CurrencyRepository {

    lateinit var currencyobservable :Single<com.example.domain.entity.currency.CurrencyModel>
    lateinit var convertcurrencyobservable :Single<ConvertModel>
    var currencylist:ArrayList<com.example.domain.entity.currency.Currency> = ArrayList()
    var currencyvaluelist:ArrayList<String> = ArrayList()


    override fun get_currency():Single<CurrencyModel>  {
        currencyobservable = currency_api.get_currency(Constants.key)
        return currencyobservable
    }

    override fun convert_currency(
        currencyFromKey: String,
        currencyToKey: String,
        amount: Double): Single<ConvertModel> {
        convertcurrencyobservable = currency_api.convert(Constants.key,currencyFromKey,currencyToKey,amount)
        return convertcurrencyobservable
    }

    override fun get_currency_list_from_local_db(): ArrayList<com.example.domain.entity.currency.Currency> {
        currencylist = databaseClass.dao?.get_all_currency_symbols() as ArrayList<com.example.domain.entity.currency.Currency>
        return currencylist
    }

    override fun get_currency_list_from_api(currencymodel: CurrencyModel?): ArrayList<com.example.domain.entity.currency.Currency> {
        currencymodel?.symbols?.keys?.let { keys ->
            for (key in keys) {
                val currency = currencymodel.symbols.getValue(key).let {
                    Currency(key, it)
                }
                currency.let { currencylist.add(it) }
                databaseClass.dao?.AddCurrencySymbols(currency)
            }
        }
        return currencylist
    }

    override fun get_currency_value_list_from_local_db(): ArrayList<String> {
        for (currency in currencylist) {
            currencyvaluelist.add(currency.key)
            //currencybasevaluelist.add(currency.key)
        }
        return currencyvaluelist
    }

    override fun get_currency_value_list_from_api(): ArrayList<String> {
        for (currency in currencylist) {
            currencyvaluelist.add(currency.key)
        }
        return currencyvaluelist
    }

    override fun save_currency_converter(
        from: String,
        to: String,
        rate: String,
        amount: String,
        result: String,
        date: String
    ) {
        dbHelper.addCurrency(from,to,rate,amount,result,date)
    }

}