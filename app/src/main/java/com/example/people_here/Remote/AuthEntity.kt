package com.example.people_here.Remote

import com.google.gson.annotations.SerializedName

//여기에 request, response 데이터 클래스 정의
data class MainResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("startDate") val startDate: LocalDateTime,
    @SerializedName("time") val time: Int,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("content") val content: String,
    @SerializedName("places") val places: ArrayList<MainPlace>,
    @SerializedName("categoryNames") val categoryNames: List<String>,
    @SerializedName("status") val status: String,
    @SerializedName("createdAt") val createdAt: LocalDateTime,
    @SerializedName("updatedAt") val updatedAt: LocalDateTime
)

data class MainPlace(
    @SerializedName("id") val id: Int,
    @SerializedName("content") val content: String,
    @SerializedName("imageUrls") val imageUrls: List<String>,
    @SerializedName("address") val address: String,
    @SerializedName("order") val order: Int
)

data class SignInRequest(//로그인에 보낼거
    @SerializedName("email")val email:String,
    @SerializedName("password")val password:String,
    //아이디 비번만 받게
    //SerializedName이란:HTTP 통신 요청 들어갈때 저 문자열 안에 key값으로 매핑 들어감
)
data class SignInResponse<T>(//닉네임과 토큰 얘네 Shared에 저장되게 하기 putString으로
    @SerializedName("userId")val userId:String,
    @SerializedName("token")val token:T,
)
data class JwtToken(
    @SerializedName("grantType") val grantType: String,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)