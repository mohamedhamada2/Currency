package com.example.currency.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.currency.R
import com.example.currency.api.Api
import com.example.currency.dagger.MyApplication
import com.example.currency.data.models.currency.Currency
import com.example.currency.data.models.currency.CurrencyModel
import com.example.currency.data.room.DatabaseClass
import com.example.currency.data.sqlite.DBHelper
import com.example.currency.databinding.ActivityMain2Binding
import com.example.currency.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Retrofit
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(activityMainBinding.root)
        //homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


        /*activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(activityMainBinding.root)
        activityMainBinding.mainviewmodel = mainViewModel1
        /*databaseClass = Room.databaseBuilder(getApplicationContext(),
            DatabaseClass::class.java, "my_orders2"
        ).allowMainThreadQueries().build()*/
        currencyvaluelist = ArrayList()
        currencybaselist = ArrayList()
        currencybasevaluelist = ArrayList()
        //val api: Api = retrofit.create(Api::class.java)
        mainViewModel1.get_currency(this)
        activityMainBinding.fromSpinner.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long) {
                currency_from_key = currencylist.get(position).key
                try {
                    val textView = view as TextView
                    textView.setTextColor(resources.getColor(R.color.black))
                } catch (e: java.lang.Exception) {
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        activityMainBinding.toSpinner.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long) {
                //currencybasevalue = currencybaselist.get(position).value
                currency_to_key = currencylist.get(position).key
                activityMainBinding.etInput.addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(cs: CharSequence?, arg1: Int, arg2: Int, arg3: Int) {
                        if (cs.toString() != ""){
                            var amount = cs.toString()
                            mainViewModel1.convert_currency(currency_from_key,currency_to_key,amount.toDouble())
                        }
                    }

                    override fun beforeTextChanged(arg0: CharSequence?, arg1: Int, arg2: Int, arg3: Int) {

                    }

                    override fun afterTextChanged(arg0: Editable?) {

                    }
                })
                if (activityMainBinding.etInput.text.toString() != ""){
                    mainViewModel1.convert_currency(currency_from_key,currency_to_key,activityMainBinding.etInput.text.toString().toDouble())
                }
                //Toast.makeText(M)
                //city_name = allCities.get(position).getCityName()
                //allCity.setCityId(city_id)
                //allCity.setCityName(city_name)
                try {
                    val textView = view as TextView
                    textView.setTextColor(resources.getColor(R.color.black))
                } catch (e: java.lang.Exception) {
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        /*activityMainBinding.btnSave.setOnClickListener(View.OnClickListener {
            /*var currencyExchange = CurrencyExchange(currency_key,currencybasekey,currencybasevalue,activityMainBinding.etInput.text.toString(),all_value.toString(),date)
            databaseClass!!.dao!!.AddExchange(currencyExchange)*/
            db.addCurrency(currency_from_key,currency_to_key,currencybasevalue,activityMainBinding.etInput.text.toString(),all_value.toString(),date)
            Toast.makeText(this,"saved successfully",Toast.LENGTH_LONG).show()
        })*/
        activityMainBinding.detail.setOnClickListener(View.OnClickListener {
            go_to_details()
        })
        /*activityMainBinding.swapImg.setOnClickListener(View.OnClickListener {
            swap(api)
        })
        mainViewModel1.currencybaseMutableLiveData.observe(this, Observer {
            set_currenct_base(it)
        })*/
        mainViewModel1.currencyMutableLiveData.observe(this, Observer<CurrencyModel> {

                //Log.d("list", t!!.symbols.size.toString())
                //set_currency(it)

        })
        mainViewModel1.currencyerrorLiveData.observe(this, Observer {
            set_currency_error(it)
        })
        mainViewModel1.convertcurrencyLiveData.observe(this, Observer {
            activityMainBinding.etOutput.setText(it.result.toString())
            mainViewModel1.addCurrency(it.query.from,it.query.to,it.info.rate.toString(),it.query.amount.toString(),it.result.toString(),it.date)
        })*/

    }

    /*private fun set_currency_error(currencies: List<Currency>) {
        currencylist = currencies as ArrayList<Currency>
        for (currency in currencylist) {
            currencyvaluelist.add(currency.key)
            currencybasevaluelist.add(currency.key)
        }
        //t!!.symbols.toString().length
        convertfromAdapter = ArrayAdapter(this@MainActivity, R.layout.spinner_item,currencyvaluelist)
        converttoAdapter = ArrayAdapter(this@MainActivity,
            R.layout.spinner_item,currencybasevaluelist)
        activityMainBinding.fromSpinner.adapter = convertfromAdapter
        activityMainBinding.toSpinner.adapter = converttoAdapter
    }

    private fun go_to_details() {
        var intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }

    private fun swap(api: Api) {
        activityMainBinding.fromSpinner.adapter = converttoAdapter
        activityMainBinding.toSpinner.adapter = convertfromAdapter
        val spinnerPosition: Int = convertfromAdapter.getPosition(currency_to_key)
        val spinnerPosition2: Int = converttoAdapter.getPosition(currency_from_key)
        activityMainBinding.fromSpinner.setSelection(spinnerPosition)
        activityMainBinding.toSpinner.setSelection(spinnerPosition2)
        //mainViewModel1.convert_currency(currency_to_key,currency_from_key,activityMainBinding.etInput.text.toString().toDouble())
    }

    /*private fun set_currency(t: CurrencyModel?) {
        for (keys in t!!.symbols.keys) {
            var value = t!!.symbols.getValue(keys)
            var currency = Currency(keys,value)
            //databaseClass?.dao?.AddCurrencySymbols(currency)
            currencylist.add(currency)
            // myCode;

        }
        for (currency in currencylist) {
            currencyvaluelist.add(currency.key)
        }
        //t!!.symbols.toString().length

    }*/

    fun setSpinnerAdapter(currencylist: ArrayList<Currency>, currencyvaluelist: ArrayList<String>) {
        this.currencylist = currencylist
        this.currencyvaluelist = currencyvaluelist
        convertfromAdapter = ArrayAdapter(this@MainActivity, R.layout.spinner_item,currencyvaluelist)
        converttoAdapter = ArrayAdapter(this@MainActivity, R.layout.spinner_item,currencyvaluelist)
        activityMainBinding.fromSpinner.adapter = convertfromAdapter
        activityMainBinding.toSpinner.adapter = converttoAdapter
    }*/

    /*private fun set_currenct_base(it: CurrencyBase?) {
        date = it!!.date
        currencybaselist.clear()
        for (keys in it!!.rates.keys) {
            var value = it!!.rates.getValue(keys)
            var currency = Currency(keys,value)
            currencybaselist.add(currency)
            // myCode;
        }
        for (currency in currencybaselist) {
            currencybasevaluelist.add(currency.key)
        }
        //t!!.symbols.toString().length
        converttoAdapter = ArrayAdapter(this@MainActivity,R.layout.spinner_item,currencybasevaluelist)
        activityMainBinding.toSpinner.adapter = converttoAdapter
    }*/
}