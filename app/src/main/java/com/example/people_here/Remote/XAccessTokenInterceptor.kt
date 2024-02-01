package com.example.people_here.Remote

import android.util.Log
import com.example.people_here.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.people_here.Local.getJwt
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


// jwtToken
class XAccessTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken : String? = getJwt()

        jwtToken?.let {
            builder.addHeader(X_ACCESS_TOKEN, "Bearer $jwtToken")
        }
        Log.d("aaaaa",builder.build().header(X_ACCESS_TOKEN).toString())


        return chain.proceed(builder.build())
    }
}