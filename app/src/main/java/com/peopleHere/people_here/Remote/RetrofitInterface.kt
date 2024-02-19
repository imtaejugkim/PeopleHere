package com.peopleHere.people_here.Remote

import com.peopleHere.people_here.Data.ChatData
import com.peopleHere.people_here.Data.ProfileData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
// 레트로핏 인터페이스, post, get, patch 등 정의
interface RetrofitInterface {
    @POST("api/auth/login-email")//login부분
    fun singin(//로그인 함수 만들고
        @Body request:SignInRequest//토큰을 받아야하나?
    ): Call<BaseResponse<SignInResponse>>//이론때 핸던 것 중 call방식으로 받겠다

    @POST("api/auth/login-phone")//login부분
    fun signinPhone(//로그인 함수 만들고
        @Body request:SignInPhoneRequest//토큰을 받아야하나?
    ): Call<BaseResponse<SignInPhoneResponse>>//이론때 핸던 것 중 call방식으로 받겠다
    @GET("api/tours")
    fun mainInfo(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String>
    ) : Call<BaseResponse<MainResponse>>

    @GET("api/tours/{id}")
    fun courseContentsInfo(@Path("id") id : Int)
    : Call<BaseResponse<CourseContentsResponse>>


    @GET("api/tour-dates/{tourId}/dates")
    fun upcomingDateInfo(@Path("tourId") tourId : Int)
    : Call<BaseResponse<ArrayList<UpcomingDateResponse>>>

    @GET("api/users/{id}/tours")
    fun bringCourseInfo(@Path("id") id : Int, @Query("option") option : String)
            : Call<BaseResponse<ArrayList<BringCourseResponse>>>
    @GET("/api/users/tour-dates")
    fun ProfileInfo()
            : Call<BaseResponse<ProfileData>>

    @GET("/api/tour-dates/participants/{tourId}")
    fun ChatUpdate(@Path("tourId") tourId : Int)
            : Call<BaseResponse<ArrayList<ChatData>>>


    @POST("api/users/wishlist/{tourId}")
    fun changeWishInfo(@Path("tourId") tourId : Int)
            : Call<BaseResponse<ChangeWishResponse>>

    @GET("/api/auth/check-email")
    fun checkEmail(@Query("email") email: String) : Call<BaseResponse<CheckEmailResponse>>


    @GET("/api/auth/check-phone")
    fun checkPhoneNumber(@Query("phoneNumber") phoneNumber: String) : Call<BaseResponse<CheckPhoneNumberResponse>>


    @POST("/api/auth/signup-email")
    fun signup(
        @Body request: SignUpRequest
    ):Call<BaseResponse<SignUpResponse>>
    @POST("/api/auth/signup-phone")
    fun signupPhone(
        @Body request: SignUpPhoneRequest
    ):Call<BaseResponse<SignUpPhoneResponse>>




    @PUT("/api/users/password")
    fun changePassword(
        @Body request: String
    ): Call<ChangePasswordResponse>

    @GET("api/tour-dates/{tourDateId}/date")
    fun requestEnjoyInfo(@Path("tourDateId") tourDateId : Int)
            : Call<BaseResponse<RequestEnjoyResponse>>

    @POST("api/tour-dates/{tourDateId}/join")
    fun joinConfirmInfo(@Path("tourDateId") tourDateId : Int)
            : Call<BaseResponse<String>>


}
