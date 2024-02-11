package com.peopleHere.people_here.Remote

import com.google.gson.annotations.SerializedName
import com.peopleHere.people_here.Data.MainCourseData
import com.peopleHere.people_here.Data.MainData


//여기에 request, response 데이터 클래스 정의
data class MainResponse(
    @SerializedName("size") val size : Int,
    @SerializedName("totalPages") val totalPages : Int,
    @SerializedName("currentPage") val currentPage : Int,
    @SerializedName("content") val content : ArrayList<MainData>,
    @SerializedName("totalElements") val totalElements : Int
)

data class CourseContentsResponse (
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
    @SerializedName("updatedAt") val updatedAt: String
)