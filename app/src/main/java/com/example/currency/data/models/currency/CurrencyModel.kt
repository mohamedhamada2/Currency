package com.example.currency.data.models.currency

data class CurrencyModel(val success: Boolean, val symbols: HashMap<String, String>, val error: Error)