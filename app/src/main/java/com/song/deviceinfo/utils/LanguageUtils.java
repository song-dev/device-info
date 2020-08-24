package com.song.deviceinfo.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;

import java.util.Locale;

import androidx.preference.PreferenceManager;

/**
 * Created by chensongsong on 2020/8/20.
 */
public class LanguageUtils {

    public static Context updateResources(Context context, String language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateLanguageInHigher(context, language);
        } else {
            return updateLanguageInLower(context, language);
        }
    }

    public static String getDefaultLanguage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String language = Locale.getDefault().getLanguage();
        String lang = preferences.getString("settings_language", language);
        LogUtils.d("SharedPreferences settings_language: " + lang);
        if (TextUtils.isEmpty(lang) || TextUtils.equals(lang, "default")) {
            lang = language;
        }
        LogUtils.d("SharedPreferences Language: " + lang);
        return lang;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateLanguageInHigher(Context context, String language) {
        Resources resources = context.getResources();
        Locale locale = new Locale(language);
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        LocaleList localeList = new LocaleList(locale);
        LocaleList.setDefault(localeList);
        configuration.setLocales(localeList);
        return context.createConfigurationContext(configuration);
    }

    private static Context updateLanguageInLower(Context context, String language) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(language);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
            return context.createConfigurationContext(configuration);
        } else {
            configuration.locale = locale;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            return context;
        }
    }

    @SuppressWarnings("deprecation")
    public static void changeAppLanguage(Context context, String language) {
        Resources resources = context.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        Locale locale = new Locale(language);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            configuration.setLocale(locale);
            configuration.setLocales(localeList);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

}
