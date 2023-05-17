package com.example.currency.view.details

import android.content.Context
import android.content.res.Resources
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.currency.R
import com.example.currency.data.language.LocaleHelper
import com.example.currency.data.room.CurrencyExchange
import com.example.currency.databinding.CurrencyItemBinding
import javax.inject.Inject
import javax.inject.Named

class DetailsAdapter (private val mList: List<CurrencyExchange?>,var language:String) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {
    var  context: Context? = null
    lateinit var resources :Resources
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = LocaleHelper.setLocale(parent.context, language)
        resources = context?.resources!!
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
                binding.txtTotal.text= this?.total
                binding.txtCurrencyBase.text= this?.base
                binding.amount.text = resources.getString(R.string.amount)
                binding.date.text = resources.getString(R.string.date)
                binding.from.text = resources.getString(R.string.from)
                binding.total.text = resources.getString(R.string.total)
                binding.currencyBase.text = resources.getString(R.string.base)

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