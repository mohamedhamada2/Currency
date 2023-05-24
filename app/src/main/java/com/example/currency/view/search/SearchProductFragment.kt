package com.example.currency.view.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currency.R
import com.example.currency.data.models.search.SearchModel
import com.example.currency.databinding.FragmentBottomSheetBinding

import com.example.currency.viewmodel.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchProductFragment : Fragment() {
    lateinit var fragmentBottomSheetBinding: FragmentBottomSheetBinding
    lateinit var search_recycler_view: RecyclerView
    lateinit var searchAdapter: SearchAdapter
    var searchList: ArrayList<SearchModel> = ArrayList()
    lateinit var linearLayoutManager : LinearLayoutManager
    private val searchViewModel :SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentBottomSheetBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet, container, false)
        fragmentBottomSheetBinding.searchproductviewmodel = searchViewModel
        search_recycler_view = fragmentBottomSheetBinding.searchRecycler
        fragmentBottomSheetBinding.backImg.setOnClickListener(View.OnClickListener {
            fragmentBottomSheetBinding.root.findNavController().navigate(R.id.action_bottomSheetFragment_to_searchFragment)
        })
        searchViewModel.searchproductmutableLiveData.observe(viewLifecycleOwner){
            searchList = it
            init_recycler_view(searchList)
        }

        // on below line we are initializing our adapter


        // on below line we are adding data to our list


        fragmentBottomSheetBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(msg)
                return false
            }
        })
        return fragmentBottomSheetBinding.root
    }

    private fun init_recycler_view(it: ArrayList<SearchModel>) {
        searchAdapter = SearchAdapter(it,this)
        search_recycler_view.adapter = searchAdapter
        linearLayoutManager = LinearLayoutManager(activity)
        search_recycler_view.layoutManager = linearLayoutManager
        searchAdapter.notifyDataSetChanged()
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<SearchModel> = ArrayList()

        // running a for loop to compare elements.
        for (item in searchList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.Name.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(activity, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            searchAdapter.filterList(filteredlist)
        }
    }

    fun sendData(name: String) {
        val bundle = Bundle()
        bundle.putString("name", name)
        fragmentBottomSheetBinding.root.findNavController().navigate(R.id.action_bottomSheetFragment_to_productsFragment, bundle)
    }
}