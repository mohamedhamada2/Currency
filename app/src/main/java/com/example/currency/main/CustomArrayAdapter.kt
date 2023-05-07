package com.example.currency.main
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.currency.R
import com.example.currency.data.models.currency.Currency

class CustomArrayAdapter (context: Context, var currencyitems: ArrayList<Currency>)
    : ArrayAdapter<Currency>(context, currencyitems.size, currencyitems) {

    val inflater: LayoutInflater = LayoutInflater.from(context)

    // Custom for this adapter. It's only here as an example



    // If required, get the ID from your Model. If your desired return value can't be converted to long use getItem(int) instead
    override fun getItemId(position: Int): Long {
        return currencyitems[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView
        if (view == null) {
            view = inflater.inflate(R.layout.spinner_item, parent, false)
        }
        (view?.findViewById(R.id.txt) as TextView).text = getItem(position)!!.key
        return view
    }

    override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
        var view: View? = convertView
        if (view == null) {
            view = inflater.inflate( R.layout.spinner_item, parent, false)
        }
        (view?.findViewById(R.id.txt) as TextView).text = getItem(position)!!.key
        return view
    }
    fun setvalue(currencyitemslist: List<Currency?>) {
        for (currency in currencyitemslist) {
            if (currency != null) {
                currencyitems.add(currency)
            }
        }
        notifyDataSetChanged()
    }
}