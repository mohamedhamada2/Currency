package com.example.data.repo.search

import com.example.domain.entity.search.SearchModel
import com.example.domain.repo.search.SearchProductsRepository

class SearchProductsRepositoryImp : SearchProductsRepository {
     var searchList: ArrayList<SearchModel> = ArrayList()
    override fun get_products(): ArrayList<SearchModel> {
        searchList.add(SearchModel("1","Android Development"))
        searchList.add(SearchModel("2", "C++ Development"))
        searchList.add(SearchModel("3", "Java Development"))
        searchList.add(SearchModel("4", "Python Development"))
        searchList.add(SearchModel("5", "JavaScript Development"))
        return searchList
    }

}