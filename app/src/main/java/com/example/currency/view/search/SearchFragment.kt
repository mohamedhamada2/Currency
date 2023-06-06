package com.example.currency.view.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.currency.R
import com.example.data.language.LocaleHelper
import com.example.currency.databinding.FragmentSearchBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    lateinit var fragmentSearchBinding: FragmentSearchBinding
    lateinit var language:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        val  view = fragmentSearchBinding.root
        fragmentSearchBinding.etSearch.setOnClickListener {
            fragmentSearchBinding.root.findNavController()
                .navigate(R.id.action_searchFragment_to_bottomSheetFragment)

        }
        LoadLocal()
        return view
    }
    private fun LoadLocal() {
        val sharedPreferences = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
        language = sharedPreferences.getString("language","")!!
        LocaleHelper.setLocale(requireContext(),language)
        fragmentSearchBinding.etSearch.hint = getString(R.string.search_product)
    }
}