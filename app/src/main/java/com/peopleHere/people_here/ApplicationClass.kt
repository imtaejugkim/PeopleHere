package com.peopleHere.people_here

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.peopleHere.people_here.Data.Latitude
import com.peopleHere.people_here.Data.PlaceData
import com.peopleHere.people_here.Data.PlacesImageData
import com.peopleHere.people_here.Remote.XAccessTokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass: Application() {

    companion object{//공통 변수를 가지고 있는 객체 ->싱글톤, 클래스에 포함된 오브젝트
    var X_ACCESS_TOKEN : String = "Authorization"

        //x-access-token 방식과 authorization 방식 여러 ㅅ개가 존재, token 값을 바꿔야 함
        const val DEV_URL : String = "http://54.180.162.207:8080"
        const val PROD_URL : String = "http://kuit_prod_url"

        const val BASE_URL : String = DEV_URL
        lateinit var retrofit : Retrofit
        lateinit var mSharedPreferencesManager: SharedPreferences//SharedPreferences받음 즉 전체에서 이거 사용 가능
        //PostDat 앞에 다 p 붙임


        var puserId: Int? = null
        var ptourName: String? = null
        var ptourTime: Int? = null
        var ptourContent: String? = null
        var pcategoryNames: MutableList<String>? =  mutableListOf()
        var pplaces: MutableList<PlaceData>? = mutableListOf()
        var pplaceName: MutableList<String>? = mutableListOf()
        var pplaceImage: MutableList<PlacesImageData>? = mutableListOf()

        var pencodingString: MutableList<String>? = mutableListOf()
        var poriginalFileName: MutableList<String>? = mutableListOf()

        var pplaceAddress: MutableList<String>? = mutableListOf()
        var platLng: MutableList<Latitude>? = mutableListOf()
        var platLngx:MutableList<Double>?= mutableListOf()
        var platLngy:MutableList<Double>?= mutableListOf()




    }
    val client : OkHttpClient = OkHttpClient.Builder()
        .readTimeout(30000, TimeUnit.MILLISECONDS)
        .connectTimeout(30000, TimeUnit.MILLISECONDS)
        .addNetworkInterceptor(XAccessTokenInterceptor())
        .build()
    //client : header를 넣을 때 client를 활용하여 넣음

    override fun onCreate() {//제일 먼저 시작
        super.onCreate()//레트로핏 만듬
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mSharedPreferencesManager=applicationContext.getSharedPreferences("My App spf", Context.MODE_PRIVATE)//이건 shared에서 값 가져오는거
    }


}