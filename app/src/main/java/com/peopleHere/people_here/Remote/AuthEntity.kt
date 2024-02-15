package com.peopleHere.people_here.Remote

import com.google.gson.annotations.SerializedName
import com.peopleHere.people_here.Data.MainData


//여기에 request, response 데이터 클래스 정의
data class MainResponse(
    @SerializedName("size") val size : Int,
    @SerializedName("totalPages") val totalPages : Int,
    @SerializedName("currentPage") val currentPage : Int,
    @SerializedName("content") val content : ArrayList<MainData>,
    @SerializedName("totalElements") val totalElements : Int
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
//로그인 되어있나 체크하는 req

data class SignUpRequest(
    @SerializedName("email")val email:String,
    @SerializedName("password")val password:String,
    @SerializedName("firstName")val firstName:String,
    @SerializedName("lastName")val lastName:String,
    @SerializedName("birth")val birth:String,
    @SerializedName("gender")val gender:String,
)
data class SignUpResponse(
    @SerializedName("userId")val userId:Int,
)

data class CheckEmailResponse(
    @SerializedName("message")val message:String,
    @SerializedName("emailAvailable")val emailAvailable:Boolean,
)