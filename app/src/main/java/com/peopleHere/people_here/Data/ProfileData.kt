package com.peopleHere.people_here.Data

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
data class ProfileData(
    @SerializedName("currentUserInfo") val currentUserInfo: CurrentUserData,
    @SerializedName("upcomingTours") val upcomingTours: ArrayList<UpcomingTours>,
    @SerializedName("pastTours") val pastTours: ArrayList<UpcomingTours>//안헷갈리게 하도록
)
data class UpcomingTours(
    @SerializedName("tourName") val tourName: String,
    @SerializedName("tourDateId") val tourDateId: Int,
    @SerializedName("tourDateTime") val tourDateTime: String,
    @SerializedName("oppositeUserInfo") val oppositeUserInfo: OppositeUserInfo?,
    @SerializedName("firstPlaceInfo") val firstPlaceInfo: FirstPlaceInfo
)
data class FirstPlaceInfo(
    @SerializedName("placeId") val placeId: Int,
    @SerializedName("placeName") val placeName: String,
    @SerializedName("placeImages") val placeImages: List<String>,
    @SerializedName("placeAddress") val placeAddress: String,
    @SerializedName("latLng") val latLng: LatLng,
    @SerializedName("placeOrder") val placeOrder: Int
)
data class OppositeUserInfo(
    @SerializedName("userId") val userId: Int,
    @SerializedName("userName") val userName: String,
    @SerializedName("userImageUrl") val userImageUrl: String
)
data class CurrentUserData(
    @SerializedName("userId") val userId: Int,
    @SerializedName("userName") val userName: String,
    @SerializedName("userImageUrl") val userImageUrl: String
)
