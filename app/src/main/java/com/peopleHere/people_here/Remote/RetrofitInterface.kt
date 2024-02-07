package com.peopleHere.people_here.Remote

import retrofit2.Call
import retrofit2.http.GET

// 레트로핏 인터페이스, post, get, patch 등 정의
interface RetrofitInterface {

    @GET("api/tours")
    fun mainInfo() : Call<BaseResponse<MainResponse>>

}