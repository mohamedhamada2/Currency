package com.example.domain.entity.currency

data class CurrencyModel(val success: Boolean, val symbols: HashMap<String, String>, val error: Error)