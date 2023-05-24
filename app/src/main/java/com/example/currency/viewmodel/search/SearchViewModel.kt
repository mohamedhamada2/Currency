package com.example.currency.viewmodel.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.data.models.search.SearchModel
import com.example.currency.data.models.search.SearchProductsRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(var searchProductsRepositoryImp: SearchProductsRepositoryImp):
    ViewModel() {
     var searchList : ArrayList<SearchModel> = ArrayList()
     var searchproductmutableLiveData : MutableLiveData<ArrayList<SearchModel>> = MutableLiveData<ArrayList<SearchModel>>()
     init {
         get_products()
     }
    fun get_products() {
        searchList =searchProductsRepositoryImp.get_products()
        searchproductmutableLiveData.value = searchList
    }

    override fun onCleared() {
        super.onCleared()
        searchList.clear()
        searchproductmutableLiveData.value = searchList
    }


}