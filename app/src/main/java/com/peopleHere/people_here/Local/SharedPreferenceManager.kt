package com.peopleHere.people_here.Local

import android.content.SharedPreferences
import com.peopleHere.people_here.ApplicationClass

fun getJwt(): String {
    val prefs: SharedPreferences = ApplicationClass.mSharedPreferencesManager
//    return prefs.getString("token", null) ?: ""
    return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwiaWF0IjoxNzA3NDU4MzI1LCJleHAiOjE3MDkyNTgzMjV9.fcmYm9EU-VTpfrXskQ7drWB-SAJQxTx9XH8bpysyt_Y"
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
