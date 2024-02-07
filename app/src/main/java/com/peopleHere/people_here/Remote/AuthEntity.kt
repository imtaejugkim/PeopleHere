package com.peopleHere.people_here.Remote

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

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