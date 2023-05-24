package com.example.currency.data.models.currency

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.currency.data.api.Api
import com.example.currency.constants.Constants
import com.example.currency.data.models.convert.ConvertModel
import com.example.currency.data.room.DatabaseClass
import com.example.currency.data.models.gallery.GalleryModel
import com.example.currency.data.sqlite.DBHelper
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class CurrencyRepositoryImp(var currency_api: Api,var databaseClass: DatabaseClass,var dbHelper: DBHelper): CurrencyRepository {

    lateinit var currencyobservable :Single<CurrencyModel>
    lateinit var convertcurrencyobservable :Single<ConvertModel>
    var currencylist:ArrayList<Currency> = ArrayList()
    var currencyvaluelist:ArrayList<String> = ArrayList()


    override fun get_currency():Single<CurrencyModel>  {
        currencyobservable = currency_api.get_currency(Constants.key)
        return currencyobservable
        /*compositeDisposable.add(observable.subscribe(
            { o: CurrencyModel? -> currencyMutableLiveData.value = o },
            { e: Throwable -> get_error_data(e,databaseClass) }))
        return  currencyMutableLiveData*/
    }

    override fun convert_currency(
        currencyFromKey: String,
        currencyToKey: String,
        amount: Double): Single<ConvertModel> {
        convertcurrencyobservable = currency_api.convert(Constants.key,currencyFromKey,currencyToKey,amount)
        return convertcurrencyobservable
    }

    override fun get_currency_list_from_local_db(): ArrayList<Currency> {
        currencylist = databaseClass.dao?.get_all_currency_symbols() as ArrayList<Currency>
        return currencylist
    }

    override fun get_currency_list_from_api(currencymodel: CurrencyModel?): ArrayList<Currency> {
        currencymodel?.symbols?.keys.let { keys ->
            for (key in keys!!) {
                var value = currencymodel?.symbols?.getValue(key)
                val currency = value?.let { Currency(key, it) }
                //databaseClass?.dao?.AddCurrencySymbols(currency)
                currency?.let { currencylist.add(it) }
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