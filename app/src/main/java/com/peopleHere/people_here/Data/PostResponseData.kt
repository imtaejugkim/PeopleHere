package com.peopleHere.people_here.Data

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class PostResponseData(
    @SerializedName("tourId") val tourId: Int,
    @SerializedName("tourName") val tourName: String,
    @SerializedName("userId") val userId: Int,
    @SerializedName("userName") val userName: String,
    @SerializedName("userImageUrl") val userImageUrl: String,

    @SerializedName("tourTime") val tourTime: Int,
    @SerializedName("tourContent") val tourContent: String,
    @SerializedName("places") val places: List<PlaceDataResponse>,

    @SerializedName("categoryNames") val categoryNames: List<String>,
    @SerializedName("participants") val participants: List<UserDataResponse>,
    @SerializedName("status") val status: String,
    @SerializedName("wished") val wished: Boolean,

    )

data class PlaceDataResponse(

    @SerializedName("placeId") val placeId: Int,
    @SerializedName("placeName") val placeName: String,
    @SerializedName("placeImage") val placeImage: List<String>,
    @SerializedName("placeAddress") val placeAddress: String,
    @SerializedName("latLng") val latLng: LatLng,
    @SerializedName("placeOrder") val placeOrder: Int,

    )

data class UserDataResponse(
    @SerializedName("userId") val userId: Int,
    @SerializedName("userName") val userName: String,
    @SerializedName("userImageUrl") val userImageUrl: String,

    )