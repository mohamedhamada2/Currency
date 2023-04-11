package com.example.currency.data.models.convert

data class ConvertModel(
    val date: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)