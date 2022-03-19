package com.yologger.data.datasource.pref

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import javax.inject.Inject

class SessionStore @Inject constructor(
    private val context: Context,
    private val keyPref: String,
    private val keyPrefSession: String
) {
    private val pref: SharedPreferences by lazy { context.getSharedPreferences(keyPref, Context.MODE_PRIVATE) }

    fun getSession(): Session? {
        val sessionString = pref.getString(keyPrefSession, null)
        return if (sessionString !== null) {
            val gson = Gson()
            gson.fromJson(sessionString, Session::class.java)
        } else {
            null
        }
    }

    fun putSession(session: Session) {
        val gson = Gson()
        val sessionString = gson.toJson(session)
        pref.edit().putString(keyPrefSession, sessionString).apply()
    }

    fun deleteSession() {
        pref.edit().remove(keyPrefSession).apply()
    }

    fun updateAvatarUrl(avatarUrl: String) {
        val session = getSession()
        session?.let {
            it.avatarUrl = avatarUrl
            putSession(it)
        }
    }
}