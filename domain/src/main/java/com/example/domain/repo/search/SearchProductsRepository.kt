package com.example.domain.repo.search

import com.example.domain.entity.search.SearchModel

interface SearchProductsRepository {
    fun get_products():ArrayList<SearchModel>
}