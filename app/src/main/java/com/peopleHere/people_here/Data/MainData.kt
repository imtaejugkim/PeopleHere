package com.peopleHere.people_here.Data

import com.google.gson.annotations.SerializedName

data class MainData (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("time") val time: Int,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("content") val content: String,
    @SerializedName("places") val places: ArrayList<MainCourseData>,
    @SerializedName("categoryNames") val categoryNames: List<String>,
    @SerializedName("status") val status: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
//    var mainTourListTitle : String,
//    var mainTourListTime : String,
//    var mainTourHeart : Boolean,
//    var mainTourListRegion : ArrayList<String>,
//    var mainTourListCourses: ArrayList<MainCourseData>,
)

//data class MainPlace(
//    @SerializedName("id") val id: Int,
//    @SerializedName("content") val content: String,
//    @SerializedName("imageUrls") val imageUrls: List<String>,
//    @SerializedName("address") val address: String,
//    @SerializedName("order") val order: Int
//)