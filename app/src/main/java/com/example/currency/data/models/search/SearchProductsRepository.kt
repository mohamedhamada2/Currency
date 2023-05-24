package com.example.currency.data.models.search

interface SearchProductsRepository {
    fun get_products():ArrayList<SearchModel>
}