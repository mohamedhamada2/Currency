package com.example.currency.viewmodel.home
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.repo.currency.CurrencyRepositoryImp
import com.example.domain.entity.convert.ConvertModel
import com.example.domain.entity.currency.Currency
import com.example.domain.entity.currency.CurrencyModel
import com.example.domain.usecase.currency.GetCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCurrency: GetCurrency, @Named("network_connection")private val connect_network:Boolean) : ViewModel() {

    var currencyMutableLiveData: MutableLiveData<com.example.domain.entity.currency.CurrencyModel?> = MutableLiveData<CurrencyModel?>()
    var convertcurrencyLiveData: MutableLiveData<ConvertModel> = MutableLiveData<ConvertModel>()
    var currencyLiveData :MutableLiveData<ArrayList<Currency>> = MutableLiveData<ArrayList<Currency>>()
    var currencyvalueMutableLiveData: MutableLiveData< ArrayList<String>> = MutableLiveData<ArrayList<String>>()
    var errorMutableLiveData :MutableLiveData<String> = MutableLiveData<String>()
    var languageMutableLiveData :MutableLiveData<String> = MutableLiveData()
    lateinit var currency_single :Single<CurrencyModel>
    lateinit var convert_currency_single: Single<ConvertModel>
    var compositeDisposable:CompositeDisposable = CompositeDisposable()
    var currencylist: ArrayList<Currency> = ArrayList()
    var  currencyvaluelist : ArrayList<String> = ArrayList()
    init {
        get_currency()
    }


    fun get_currency() {
        if (connect_network){
            currency_single = getCurrency.get_currency().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            compositeDisposable.add(currency_single.subscribe(
                { o: CurrencyModel? -> setCurrencyData(o) },
                { e: Throwable -> get_error_data(e) }))
        }else{
            get_currency_from_local_db()
        }
    }

    private fun get_currency_from_local_db() {
        currencylist = getCurrency.get_currency_list_from_local_db()
        currencyvaluelist = getCurrency.get_currency_value_list_from_local_db()
        currencyLiveData.value = currencylist
        currencyvalueMutableLiveData.value = currencyvaluelist
    }

    private fun get_error_data(e: Throwable) {
        errorMutableLiveData.value = e.message

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun setCurrencyData(currencymodel: CurrencyModel?) {
        currencylist = getCurrency.get_currency_list_from_api(currencymodel)
        currencyvaluelist = getCurrency.get_currency_value_list_from_api()
        currencyLiveData.value = currencylist
        currencyvalueMutableLiveData.value = currencyvaluelist

    }
    fun convert_currency(currencyFromKey: String, currencyToKey: String, amount: Double) {
        if (connect_network) {
            convert_currency_single =
                getCurrency.convert_currency(currencyFromKey, currencyToKey, amount)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            compositeDisposable.add(
                convert_currency_single.subscribe(
                    { o: ConvertModel? -> convertcurrencyLiveData.value = o },
                    { e: Throwable -> Log.d("convert_error", "$e") })
            )
        }
    }

    fun addCurrency(from: String, to: String, rate: String, amount: String, result: String, date: String) {
        //dbHelper.addCurrency(from,to,rate,amount,result,date)
        getCurrency.save_currency_converter(from,to,rate,amount,result,date)
    }
    /*fun addCurrency(from: String, to: String, rate: String, amount: String, result: String, date: String) {
        dbHelper.addCurrency(from,to,rate,amount,result,date)
    }*/
}