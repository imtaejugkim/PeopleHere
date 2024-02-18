package com.peopleHere.people_here.Data

import com.google.android.gms.maps.model.LatLng

data class MakingTourAddListData (
    var placeImage : String,
    var placeName: String,
    var placeAddress : String,
    var placeLocation : LatLng
)