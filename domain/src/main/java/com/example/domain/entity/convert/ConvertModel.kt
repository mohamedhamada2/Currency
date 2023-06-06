package com.example.domain.entity.convert

import com.example.domain.entity.convert.Info
import com.example.domain.entity.convert.Query

data class ConvertModel(
    val date: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)