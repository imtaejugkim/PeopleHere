package com.peopleHere.people_here.Remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// 레트로핏 인터페이스, post, get, patch 등 정의
interface RetrofitInterface {

    @GET("api/tours")
    fun mainInfo(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String>
    ) : Call<BaseResponse<MainResponse>>

    @GET("api/tours/{id}")
    fun courseContentsInfo(@Path("id") id : Int)
    : Call<BaseResponse<CourseContentsResponse>>

}