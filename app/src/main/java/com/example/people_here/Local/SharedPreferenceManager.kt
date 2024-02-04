package com.example.people_here.Local

import android.content.SharedPreferences
import com.example.people_here.ApplicationClass

fun getJwt(): String {
    val prefs: SharedPreferences = ApplicationClass.mSharedPreferencesManager
//    return prefs.getString("token", null) ?: ""
    return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwiaWF0IjoxNzA3MDU1NjExLCJleHAiOjE3MDg4NTU2MTF9.LdDzQcRyW2m0IcqibaA7mdV7BxPGoMtjMsdHM6OfMog"
}
fun removeJwt() {
    ApplicationClass.mSharedPreferencesManager.edit().apply {
        remove("token")
        apply()
    }
}

fun saveJwt(token: String) {
    ApplicationClass.mSharedPreferencesManager.edit().apply {
        putString("token", token)
        apply()
    }
}
