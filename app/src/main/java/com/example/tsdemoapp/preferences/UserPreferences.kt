package com.example.tsdemoapp.preferences

import com.example.tsdemoapp.model.User
import android.content.Context

class UserPreferences(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    companion object {
        private const val SHARED_PREF_NAME = "dsl_user_shared_pref"
        private const val KEY_USER_ID = "dsl_user_id"
        private const val KEY_USER_NAME = "dsl_user_name"
        private const val KEY_USER_EMAIL = "dsl_user_email"
        private const val KEY_USER_TOKEN = "dsl_user_token"
    }

    fun saveUser(user: User, token: String?) {
        editor.putInt(KEY_USER_ID, user.id)
        editor.putString(KEY_USER_NAME, user.name)
        editor.putString(KEY_USER_EMAIL, user.email)
        editor.putString(KEY_USER_TOKEN, token)
        editor.apply()
    }

    fun getUser(): User? {
        val id = sharedPreferences.getInt(KEY_USER_ID, -1)
        if (id == -1) return null

        return User(
            id = id,
            name = sharedPreferences.getString(KEY_USER_NAME, "") ?: "",
            email = sharedPreferences.getString(KEY_USER_EMAIL, "") ?: "",
            token = sharedPreferences.getString(KEY_USER_TOKEN, "") ?: ""
        )
    }

    fun clearUser() {
        editor.clear().apply()
    }
}
