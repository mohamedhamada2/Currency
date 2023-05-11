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
import com.example.currency.data.models.currency.CurrencyModel
import com.example.currency.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var currencylist: ArrayList<Currency>
    var currencyvaluelist: ArrayList<String> =  ArrayList()
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
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        fragmentHomeBinding.mainviewmodel = mainViewModel1
        val view = fragmentHomeBinding.root
        init()



        /*fragmentHomeBinding.detail.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
            //go_to_details()
        }*/
        fragmentHomeBinding.swapImg.setOnClickListener(View.OnClickListener {
            swap()
        })
        /*mainViewModel1.currencybaseMutableLiveData.observe(this, Observer {
            set_currenct_base(it)
        })*/

        mainViewModel1.convertcurrencyLiveData.observe(viewLifecycleOwner, Observer {
            fragmentHomeBinding.etOutput.setText(it.result.toString())
            mainViewModel1.addCurrency(it.query.from,it.query.to,it.info.rate.toString(),it.query.amount.toString(),it.result.toString(),it.date)
        })
        return view
    }

    private fun init() {
        mainViewModel1.currencyLiveData.observe(viewLifecycleOwner) {
            //set_currency(it)
            currencylist = it
        }
        mainViewModel1.currencyvalueMutableLiveData.observe(viewLifecycleOwner) {
            //set_currency(it)
            currencyvaluelist = it
            setSpinnerAdapter(currencyvaluelist)
        }
    }

    /*private fun set_currency(currencymodel: CurrencyModel?) {
        for (keys in currencymodel?.symbols!!.keys) {
            var value = currencymodel?.symbols!!.getValue(keys)
            var currency = Currency(keys, value)
            //databaseClass?.dao?.AddCurrencySymbols(currency)
            currencylist.add(currency)
            databaseClass.dao?.AddCurrencySymbols(currency)
            currencyMutableLiveData.value = currencymodel
            for (currency in currencylist) {
                currencyvaluelist.add(currency.key)
            }
            homeFragment.setSpinnerAdapter(currencylist,currencyvaluelist)
        }
    }*/


    /*private fun set_currency_error(currencies: List<Currency>?) {
        currencylist = currencies as ArrayList<Currency>
        for (currency in currencylist) {
            currencyvaluelist.add(currency.key)
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
    }*/

    private fun setSpinnerAdapter(currencyvaluelist: ArrayList<String>) {
        this.currencyvaluelist = currencyvaluelist
        convertfromAdapter =
            ArrayAdapter(requireActivity(), R.layout.spinner_item, currencyvaluelist)
        converttoAdapter = ArrayAdapter(requireActivity(), R.layout.spinner_item, currencyvaluelist)
        fragmentHomeBinding.fromSpinner.adapter = convertfromAdapter
        fragmentHomeBinding.toSpinner.adapter = converttoAdapter
        setData()
    }

    private fun setData() {
        fragmentHomeBinding.fromSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long) {
                currency_from_key = currencylist[position].key
                try {
                    val textView = view as TextView
                    textView.setTextColor(resources.getColor(R.color.black))
                } catch (e: java.lang.Exception) {
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        fragmentHomeBinding.toSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long) {
                currency_to_key = currencylist[position].key
                mainViewModel1.convert_currency(currency_from_key,currency_to_key,fragmentHomeBinding.etInput.text.toString().toDouble())
                try {
                    val textView = view as TextView
                    textView.setTextColor(resources.getColor(R.color.black))
                } catch (e: java.lang.Exception) {

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
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
    }

    private fun swap() {
        fragmentHomeBinding.fromSpinner.adapter = converttoAdapter
        fragmentHomeBinding.toSpinner.adapter = convertfromAdapter
        val spinnerPosition: Int = convertfromAdapter.getPosition(currency_to_key)
        val spinnerPosition2: Int = converttoAdapter.getPosition(currency_from_key)
        fragmentHomeBinding.fromSpinner.setSelection(spinnerPosition)
        fragmentHomeBinding.toSpinner.setSelection(spinnerPosition2)
        mainViewModel1.convert_currency(currency_to_key,currency_from_key,fragmentHomeBinding.etInput.text.toString().toDouble())
    }
}