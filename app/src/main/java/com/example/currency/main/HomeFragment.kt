package com.example.currency.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.currency.R
import com.example.currency.data.models.currency.Currency
import com.example.currency.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var currencylist: ArrayList<Currency>
    lateinit var currencyvaluelist: ArrayList<String>
    lateinit var currencybaselist: ArrayList<Currency>
    lateinit var currencybasevaluelist: ArrayList<String>
    lateinit var currency_from_key:String
    lateinit var currencybasevalue: String
    lateinit var currency_to_key: String
    lateinit var date:String
    lateinit var fragmentHomeBinding: FragmentHomeBinding

    lateinit var convertfromAdapter: ArrayAdapter<String>
    lateinit var converttoAdapter: ArrayAdapter<String>
    private val mainViewModel1: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val view = fragmentHomeBinding.root;
        // Inflate the layout for this fragment
        fragmentHomeBinding.mainviewmodel = mainViewModel1
        /*databaseClass = Room.databaseBuilder(getApplicationContext(),
            DatabaseClass::class.java, "my_orders2"
        ).allowMainThreadQueries().build()*/
        currencyvaluelist = ArrayList()
        currencybaselist = ArrayList()
        currencybasevaluelist = ArrayList()
        //val api: Api = retrofit.create(Api::class.java)
        mainViewModel1.get_currency(this)
        fragmentHomeBinding.fromSpinner.setOnItemSelectedListener(object :
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
        fragmentHomeBinding.toSpinner.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long) {
                //currencybasevalue = currencybaselist.get(position).value
                currency_to_key = currencylist.get(position).key
                fragmentHomeBinding.etInput.addTextChangedListener(object : TextWatcher {
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
                if (fragmentHomeBinding.etInput.text.toString() != ""){
                    mainViewModel1.convert_currency(currency_from_key,currency_to_key,fragmentHomeBinding.etInput.text.toString().toDouble())
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
        fragmentHomeBinding.detail.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
            //go_to_details()
        }
        /*fragmentHomeBinding.swapImg.setOnClickListener(View.OnClickListener {
            swap(api)
        })
        mainViewModel1.currencybaseMutableLiveData.observe(this, Observer {
            set_currenct_base(it)
        })*/
        mainViewModel1.currencyMutableLiveData.observe(viewLifecycleOwner, {

            //Log.d("list", t!!.symbols.size.toString())
            //set_currency(it)

        })
        mainViewModel1.currencyerrorLiveData.observe(viewLifecycleOwner, Observer {
            set_currency_error(it)
        })
        mainViewModel1.convertcurrencyLiveData.observe(viewLifecycleOwner, Observer {
            fragmentHomeBinding.etOutput.setText(it.result.toString())
            mainViewModel1.addCurrency(it.query.from,it.query.to,it.info.rate.toString(),it.query.amount.toString(),it.result.toString(),it.date)
        })
        return view
    }

    private fun set_currency_error(currencies: List<Currency>?) {
        currencylist = currencies as ArrayList<Currency>
        for (currency in currencylist) {
            currencyvaluelist.add(currency.key)
            currencybasevaluelist.add(currency.key)
        }
        //t!!.symbols.toString().length
        convertfromAdapter =
            ArrayAdapter(requireActivity(), R.layout.spinner_item, currencyvaluelist)
        converttoAdapter = ArrayAdapter(
            requireActivity(),
            R.layout.spinner_item, currencybasevaluelist
        )
        fragmentHomeBinding.fromSpinner.adapter = convertfromAdapter
        fragmentHomeBinding.toSpinner.adapter = converttoAdapter
    }

    fun setSpinnerAdapter(currencylist: ArrayList<Currency>, currencyvaluelist: ArrayList<String>) {
        this.currencylist = currencylist
        this.currencyvaluelist = currencyvaluelist
        convertfromAdapter =
            ArrayAdapter(requireActivity(), R.layout.spinner_item, currencyvaluelist)
        converttoAdapter = ArrayAdapter(requireActivity(), R.layout.spinner_item, currencyvaluelist)
        fragmentHomeBinding.fromSpinner.adapter = convertfromAdapter
        fragmentHomeBinding.toSpinner.adapter = converttoAdapter
    }
}