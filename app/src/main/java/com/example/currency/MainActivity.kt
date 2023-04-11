package com.example.currency

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.currency.data.Currency
import com.example.currency.data.CurrencyBase
import com.example.currency.data.CurrencyModel
import com.example.currency.data.room.DatabaseClass
import com.example.currency.data.sqlite.DBHelper
import com.example.currency.databinding.ActivityMainBinding
import com.example.currency.details.DetailsActivity


class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding;
    lateinit var mainViewModel1: MainViewModel
    lateinit var currencylist: ArrayList<Currency>
    lateinit var currencyvaluelist: ArrayList<String>
    lateinit var currencybaselist: ArrayList<Currency>
    lateinit var currencybasevaluelist: ArrayList<String>
    lateinit var currency_from_key:String
    lateinit var currencybasevalue: String
    lateinit var currency_to_key: String
    lateinit var date:String
    var databaseClass: DatabaseClass? = null
    var all_value:Double? =0.0
    val db = DBHelper(this, null)
    lateinit var convertfromAdapter: ArrayAdapter<String>
    lateinit var converttoAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel1 = ViewModelProvider(this).get(MainViewModel::class.java)
        activityMainBinding.mainviewmodel = mainViewModel1
        /*databaseClass = Room.databaseBuilder(getApplicationContext(),
            DatabaseClass::class.java, "my_orders2"
        ).allowMainThreadQueries().build()*/
        currencylist = ArrayList()
        currencyvaluelist = ArrayList()
        currencybaselist = ArrayList()
        currencybasevaluelist = ArrayList()
        mainViewModel1.get_currency()
        activityMainBinding.fromSpinner.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long) {
                currency_from_key = currencylist.get(position).key
                Log.d("currency_key",currency_from_key)
                mainViewModel1.get_currency_base(currency_from_key)
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
        activityMainBinding.toSpinner.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long) {
                currencybasevalue = currencybaselist.get(position).value
                currency_to_key = currencybaselist.get(position).key
                //Toast.makeText(this@MainActivity, currencybasevalue+"",Toast.LENGTH_LONG).show()
                activityMainBinding.etInput.addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(cs: CharSequence?, arg1: Int, arg2: Int, arg3: Int) {
                        if (cs.toString() != ""){
                            var amount = cs.toString()
                            /*all_value = (all_value_string.toDouble()) * currencybasevalue.toDouble()
                            activityMainBinding.etOutput.setText(all_value.toString())*/
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
        activityMainBinding.swapImg.setOnClickListener(View.OnClickListener {
            swap()
        })
        mainViewModel1.currencybaseMutableLiveData.observe(this, Observer {
            set_currenct_base(it)
        })
        mainViewModel1.currencyMutableLiveData.observe(this,object : Observer<CurrencyModel> {
            override fun onChanged(t: CurrencyModel?) {
                //Log.d("list", t!!.symbols.size.toString())
                set_currency(t)
            }
        })
        mainViewModel1.convertcurrencyLiveData.observe(this, Observer {
            activityMainBinding.etOutput.setText(it.result.toString())
            db.addCurrency(it.query.from,it.query.to,it.info.rate.toString(),it.query.amount.toString(),it.result.toString(),it.date)
        })

    }

    private fun go_to_details() {
        var intent = Intent(this,DetailsActivity::class.java)
        startActivity(intent)
    }

    private fun swap() {
        activityMainBinding.fromSpinner.adapter = converttoAdapter
        activityMainBinding.toSpinner.adapter = convertfromAdapter
        val spinnerPosition: Int = convertfromAdapter.getPosition(currency_to_key)
        val spinnerPosition2: Int = converttoAdapter.getPosition(currency_from_key)
        activityMainBinding.fromSpinner.setSelection(spinnerPosition)
        activityMainBinding.toSpinner.setSelection(spinnerPosition2)
        mainViewModel1.convert_currency(currency_to_key,currency_from_key,activityMainBinding.etInput.text.toString().toDouble())
    }

    private fun set_currency(t: CurrencyModel?) {
        for (keys in t!!.symbols.keys) {
            var value = t!!.symbols.getValue(keys)
            var currency = Currency(keys,value)
            currencylist.add(currency)
            // myCode;
        }
        for (currency in currencylist) {
            currencyvaluelist.add(currency.key)
        }
        //t!!.symbols.toString().length
        convertfromAdapter = ArrayAdapter(this@MainActivity,R.layout.spinner_item,currencyvaluelist)
        activityMainBinding.fromSpinner.adapter = convertfromAdapter
    }

    private fun set_currenct_base(it: CurrencyBase?) {
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

    }
}