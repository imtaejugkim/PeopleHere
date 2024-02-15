package com.peopleHere.people_here.Local

import android.content.SharedPreferences
import com.peopleHere.people_here.ApplicationClass

fun getJwt(): String {
    val prefs: SharedPreferences = ApplicationClass.mSharedPreferencesManager
//    return prefs.getString("token", null) ?: ""
    return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUZXN0MTIzNEBrb25rdWsuYWMua3IiLCJpYXQiOjE3MDc3MjA4MjcsImV4cCI6MTcwOTUyMDgyN30.nYx-0H2z9RP3HdE2dpRvh6bVfD0fI7l_hnrB4UVtg1c"
//    아이디 : Test1234@konkuk.ac.kr
//    비밀번호 : Test123!!
//    userId : 6
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
