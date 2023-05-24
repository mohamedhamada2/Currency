package com.example.currency.data.language

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.preference.PreferenceManager
import java.util.*


class LocaleHelper {

    // the method is used to set the language at runtime
    companion object{
        fun setLocale(context: Context, language: String) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config: Configuration = context.resources.configuration
            config.locale = locale
            context.resources.updateConfiguration(
                config, context.resources.displayMetrics
            )
            // for devices having lower version of android os

        }
    }


}