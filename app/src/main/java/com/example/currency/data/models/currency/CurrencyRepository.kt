package com.example.currency.data.models.currency

import com.example.currency.data.models.convert.ConvertModel
import com.example.currency.data.models.gallery.GalleryModel

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CurrencyRepository {

    fun get_currency(): Single<CurrencyModel>
    fun convert_currency(currencyFromKey: String, currencyToKey: String, amount: Double):Single<ConvertModel>

}