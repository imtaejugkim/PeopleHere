package com.peopleHere.people_here.Remote

import com.google.gson.annotations.SerializedName
import com.peopleHere.people_here.Data.CourseScheduleData
import com.peopleHere.people_here.Data.MainCourseData
import com.peopleHere.people_here.Data.MainData
import com.peopleHere.people_here.Data.ScheduleParticipants


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
data class CheckEmailRequest(
    @SerializedName("email")val email:String,
)

data class CheckEmailResponse(
    @SerializedName("message")val message:String,
    @SerializedName("emailAvailable")val emailAvailable:Boolean,
)

data class CourseContentsResponse (
    @SerializedName("tourId") val tourId: Int,
    @SerializedName("tourName") val tourName: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("userImageUrl") val userImageUrl: String,
    @SerializedName("time") val time: Int,
    @SerializedName("content") val content : String,
    @SerializedName("places") val places: ArrayList<MainCourseData>,
    @SerializedName("categoryNames") val categoryNames: List<String>,
    @SerializedName("participants") val participants: List<Any>,
    @SerializedName("status") val status: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)

data class UpcomingDateResponse (
    @SerializedName("id") val id : Int,
    @SerializedName("date") val date : String,
    @SerializedName("time") val time : String,
    @SerializedName("status") val status : String,
    @SerializedName("participants") val participants : ArrayList<ScheduleParticipants>
)

data class BringCourseResponse (
    @SerializedName("tourId") val tourId: Int,
    @SerializedName("tourName") val tourName: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("userImageUrl") val userImageUrl: String,
    @SerializedName("time") val time: String,
    @SerializedName("content") val content : String,
    @SerializedName("places") val places: ArrayList<MainCourseData>,
    @SerializedName("categoryNames") val categoryNames: List<String>,
    @SerializedName("participants") val participants: List<Any>,
    @SerializedName("status") val status: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("wished") val wished : Boolean
)

data class ChangeWishResponse(
    @SerializedName("result") val result : String
)
