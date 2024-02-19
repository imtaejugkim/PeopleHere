package com.peopleHere.people_here.Data

import com.google.gson.annotations.SerializedName

data class ChatData(
    @SerializedName("tourHistoryId") val tourHistoryId: Int,
    @SerializedName("tourDateId") val tourDateId: Int,
    @SerializedName("date") val date: String,
    @SerializedName("time") val time: TimeData,
    @SerializedName("status") val status: String,
    @SerializedName("participants") val participants: ParticipantsData,
    )

data class TimeData(
    @SerializedName("hour") val hour: Int,
    @SerializedName("minute") val minute: Int,
    @SerializedName("second") val second: Int,
    @SerializedName("nano") val nano: Int,
    )

data class ParticipantsData(
    @SerializedName("userId") val userId: Int,
    @SerializedName("userName") val userName: String,
    @SerializedName("userImageUrl") val userImageUrl: String
)