package com.example.currency.data

data class CurrencyBase(
    val base: String,
    val date: String,
    val rates: HashMap<String, String>,
    val success: Boolean,
    val timestamp: Int
)