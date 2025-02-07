package com.peopleHere.people_here.Data

import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("userId") val userId: Int,
    @SerializedName("tourName") val tourName: String,
    @SerializedName("tourTime") val tourTime: Int,
    @SerializedName("tourContent") val tourContent: String,
    @SerializedName("categoryNames") val categoryNames: List<String>?,
    @SerializedName("places") val places: List<PlaceData>,
)
data class PlaceData(
    @SerializedName("placeName") val placeName: String,
    @SerializedName("placeImage") val placeImage: List<PlacesImageData>,
    @SerializedName("placeAddress") val placeAddress: String,
    @SerializedName("latLng") val latLng: Latitude,
    @SerializedName("placeOrder") val placeOrder: Int,
)

data class Latitude(
    @SerializedName("x") val x: Double?,
    @SerializedName("y") val y: Double?
)

data class PlacesImageData(
    @SerializedName("encodingString") val encodingString: String,
    @SerializedName("originalFileName") val originalFileName: String,
    )