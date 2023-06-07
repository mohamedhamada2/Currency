package com.example.currency.view.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.currency.R
import com.example.currency.databinding.FragmentProductsBinding


class ProductsFragment : Fragment() {
    lateinit var fragmentProductsBinding: FragmentProductsBinding
     var name: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentProductsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_products, container, false)
        getData()
        fragmentProductsBinding.backImg.setOnClickListener(View.OnClickListener {
            fragmentProductsBinding.root.findNavController().navigate(R.id.action_productsFragment_to_searchFragment)
        })
        return fragmentProductsBinding.root
    }

    private fun getData() {
        name = arguments?.getString("name")
        fragmentProductsBinding.txtTitle.text = name
    }
}