package com.example.tsdemoapp.preferences

import android.content.Context

class AppPreferences(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    companion object {
        private const val SHARED_PREF_NAME = "dsl_app_shared_pref"
        private const val KEY_APP_LANG = "dsl_app_language"
    }

    fun saveLanguage(lang: String) {
        editor.putString(KEY_APP_LANG, lang)
        editor.apply()
    }

    fun getLanguage(): String {
        val lang = sharedPreferences.getString(KEY_APP_LANG, "en") ?: "en"
        return lang;
    }
}
