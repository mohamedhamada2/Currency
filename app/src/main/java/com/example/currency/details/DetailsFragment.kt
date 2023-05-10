package com.example.currency.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currency.R
import com.example.currency.data.room.CurrencyExchange
import com.example.currency.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    lateinit var fragmentDetailsBinding: FragmentDetailsBinding
    lateinit var exchangelist : List<CurrencyExchange?>
    lateinit var detailsAdapter: DetailsAdapter
    lateinit var layout_manager : LinearLayoutManager
    private val detailsViewModel1: DetailsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        fragmentDetailsBinding.detailsviewmodel = detailsViewModel1
        detailsViewModel1.currencyMutableLiveData.observe(viewLifecycleOwner, Observer {
            setlist(it)
        })
        /*fragmentDetailsBinding.backImg.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_homeFragment)
            //go_to_details()
        }*/
        return fragmentDetailsBinding.root
    }

    private fun setlist(currencyExchanges: List<CurrencyExchange?>) {
        detailsAdapter = DetailsAdapter(currencyExchanges)
        layout_manager = LinearLayoutManager(requireContext())
        fragmentDetailsBinding.detailsRecycler.adapter = detailsAdapter
        fragmentDetailsBinding.detailsRecycler.layoutManager = layout_manager;
    }
}