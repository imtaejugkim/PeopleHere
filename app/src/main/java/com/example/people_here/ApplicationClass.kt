package com.example.people_here

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class ApplicationClass: Application() {

    companion object{//공통 변수를 가지고 있는 객체 ->싱글톤, 클래스에 포함된 오브젝트
       lateinit var mSharedPreferencesManager: SharedPreferences//SharedPreferences받음 즉 전체에서 이거 사용 가능
    }

    override fun onCreate() {//제일 먼저 시작
        super.onCreate()//레트로핏 만듬

        mSharedPreferencesManager=applicationContext.getSharedPreferences("My App spf", Context.MODE_PRIVATE)//이건 shared에서 값 가져오는거
    }


}