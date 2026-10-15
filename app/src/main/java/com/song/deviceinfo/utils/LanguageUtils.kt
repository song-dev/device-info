package com.song.deviceinfo.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.text.TextUtils
import androidx.preference.PreferenceManager
import java.util.Locale

/**
 * Utility class for handling language and locale settings
 * Created by chensongsong on 2020/8/20.
 */
object LanguageUtils {

    fun updateResources(context: Context, language: String): Context =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateLanguageInHigher(context, language)
        } else {
            updateLanguageInLower(context, language)
        }
    
    fun getDefaultLanguage(context: Context): String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val defaultLanguage = "${Locale.getDefault().language}-${Locale.getDefault().country}"
        
        return preferences.getString("settings_language", defaultLanguage)
            ?.takeUnless { it.isEmpty() || it == "default" }
            ?: defaultLanguage
            .also { lang -> LogUtils.i("Language setting: $lang") }
    }
    
    @TargetApi(Build.VERSION_CODES.N)
    private fun updateLanguageInHigher(context: Context, language: String): Context {
        val resources = context.resources
        val locale = getLocale(context, language)
        val configuration = resources.configuration.apply {
            setLocale(locale)
            val localeList = LocaleList(locale).also { LocaleList.setDefault(it) }
            setLocales(localeList)
        }
        return context.createConfigurationContext(configuration)
    }
    
    private fun updateLanguageInLower(context: Context, language: String): Context {
        val resources = context.resources
        val configuration = resources.configuration
        val locale = getLocale(context, language)
        
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> {
                configuration.setLocale(locale)
                context.createConfigurationContext(configuration)
            }
            else -> {
                configuration.locale = locale
                resources.updateConfiguration(configuration, resources.displayMetrics)
                context
            }
        }
    }
    
    private fun getLocale(context: Context, language: String): Locale {
        return try {
            language.split("-").let { parts ->
                when (parts.size) {
                    1 -> Locale(parts[0])
                    2 -> Locale(parts[0], parts[1])
                    else -> context.resources.configuration.locale
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            context.resources.configuration.locale
        }
    }
    
    @Suppress("deprecation")
    fun changeAppLanguage(context: Context, language: String) {
        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        val locale = Locale(language)
        
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                LocaleList(locale).also { localeList ->
                    LocaleList.setDefault(localeList)
                    configuration.apply {
                        setLocale(locale)
                        setLocales(localeList)
                    }
                }
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> {
                configuration.setLocale(locale)
            }
            else -> {
                configuration.locale = locale
            }
        }
        
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}
