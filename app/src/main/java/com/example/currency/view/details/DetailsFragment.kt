package com.example.currency.view.details

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currency.R
import com.example.currency.data.language.LocaleHelper
import com.example.currency.data.room.CurrencyExchange
import com.example.currency.databinding.FragmentDetailsBinding
import com.example.currency.viewmodel.details.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    lateinit var fragmentDetailsBinding: FragmentDetailsBinding
    lateinit var detailsAdapter: DetailsAdapter
    lateinit var layout_manager : LinearLayoutManager
    private val detailsViewModel1: DetailsViewModel by viewModels()
    private lateinit var language: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        fragmentDetailsBinding.detailsviewmodel = detailsViewModel1
        LoadLocal()
        detailsViewModel1.languageMutableLiveData.observe(viewLifecycleOwner){
            //init_language(it)
        }
        detailsViewModel1.currencyMutableLiveData.observe(viewLifecycleOwner, Observer {
            setlist(it)
        })
        return fragmentDetailsBinding.root
    }

    private fun setlist(currencyExchanges: List<CurrencyExchange?>) {
        Toast.makeText(activity,currencyExchanges.size.toString(),Toast.LENGTH_LONG).show()
        detailsAdapter = DetailsAdapter(currencyExchanges)
        layout_manager = LinearLayoutManager(requireContext())
        fragmentDetailsBinding.detailsRecycler.adapter = detailsAdapter
        fragmentDetailsBinding.detailsRecycler.layoutManager = layout_manager;
    }
    private fun LoadLocal() {
        val sharedPreferences = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
        language = sharedPreferences.getString("language","")!!
        LocaleHelper.setLocale(requireContext(),language)
        fragmentDetailsBinding.txtExchanges.text = getString(R.string.exchanges)
    }
}