package com.peopleHere.people_here.Data

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

data class MakingTourAddListData (
    var placeImage : String,
    var placeName: String,
    var placeAddress : String,
    var placeLocation : LatLng,
    var marker : Marker?= null
)