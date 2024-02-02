package com.example.people_here.Remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// 레트로핏 인터페이스, post, get, patch 등 정의
interface RetrofitInterface {
    @POST("/api/users/login")//login부분
    fun singin(//로그인 함수 만들고
        @Body request:SignInRequest//토큰을 받아야하나?
    ): Call<BaseResponse<SignInResponse<JwtToken>>>//이론때 핸던 것 중 call방식으로 받겠다

}