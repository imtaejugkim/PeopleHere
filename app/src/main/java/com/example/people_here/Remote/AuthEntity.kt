package com.example.people_here.Remote

import com.example.people_here.Data.MainData
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime


//여기에 request, response 데이터 클래스 정의
data class MainResponse(
    @SerializedName("size") val size : Int,
    @SerializedName("totalPages") val totalPages : Int,
    @SerializedName("currentPage") val currentPage : Int,
    @SerializedName("content") val content : ArrayList<MainData>
)