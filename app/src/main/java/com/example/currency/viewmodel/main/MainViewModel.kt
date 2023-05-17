package com.example.currency.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.intellij.lang.annotations.Language
import javax.inject.Inject
import javax.inject.Named
@HiltViewModel
class MainViewModel @Inject constructor(@Named("language") var language: String):ViewModel() {
    var languageMutableLiveData :MutableLiveData<String> = MutableLiveData()
    init {
        get_language(language)
    }

    private fun get_language(language: String) {
        languageMutableLiveData.value = language
    }
}