package com.example.currency.details

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currency.R
import com.example.currency.data.room.CurrencyExchange

class DetailsAdapter (private val mList: List<CurrencyExchange?>) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currencyExchangemodel = mList[position]

        // sets the image to the imageview from our itemHolder class

        // sets the text to the textview from our itemHolder class
        holder.txt_date.setText(currencyExchangemodel!!.date)
        holder.txt_from.setText(currencyExchangemodel!!.from_currency)
        holder.txt_to.setText(currencyExchangemodel.to_currency)
        holder.txt_total.setText(currencyExchangemodel.total)
        holder.txt_currency_base.setText(currencyExchangemodel.base)
        holder.txt_amount.setText(currencyExchangemodel.amount)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txt_date: TextView = itemView.findViewById(R.id.txt_date)
        val txt_total: TextView = itemView.findViewById(R.id.txt_total)
        val txt_from: TextView = itemView.findViewById(R.id.txt_from)
        val txt_to: TextView = itemView.findViewById(R.id.txt_to)
        val txt_amount: TextView = itemView.findViewById(R.id.txt_amount)
        val txt_currency_base: TextView = itemView.findViewById(R.id.txt_currency_base)
    }
}