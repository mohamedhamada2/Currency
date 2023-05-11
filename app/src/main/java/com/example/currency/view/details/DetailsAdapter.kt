package com.example.currency.view.details

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
import com.example.currency.databinding.CurrencyItemBinding

class DetailsAdapter (private val mList: List<CurrencyExchange?>) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val binding = CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(mList[position]){
                binding.txtDate.text = this?.date
                binding.txtAmount.text = this?.amount
                binding.txtFrom.text = this?.from_currency
                binding.txtTo.text = this?.to_currency
                binding.total.text= this?.total
                binding.total.text= this?.base

            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(val binding: CurrencyItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}