package com.peopleHere.people_here.Data

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("userId") val userId: Int,
    @SerializedName("tourName") val tourName: String,
    @SerializedName("tourTime") val tourTime: Int,
    @SerializedName("tourContent") val tourContent: String,
    @SerializedName("categoryNames") val categoryNames: MutableList<String>?,
    @SerializedName("places") val places: MutableList<PlaceData>,
)
data class PlaceData(
    @SerializedName("placeName") val placeName: String,
    @SerializedName("placeImage") val placeImage: MutableList<PlacesImageData>,
    @SerializedName("placeAddress") val placeAddress: String,
    @SerializedName("latLng") val latLng: LatLng,
    @SerializedName("placeOrder") val placeOrder: Int,
)

data class PlacesImageData(
    @SerializedName("encodingString") val encodingString: String?,
    @SerializedName("originalFileName") val originalFileName: String?,
    )