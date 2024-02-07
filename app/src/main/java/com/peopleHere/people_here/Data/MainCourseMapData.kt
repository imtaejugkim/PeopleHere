package com.peopleHere.people_here.Data

import java.io.Serializable

data class MainCourseMapData (
    var courseTitle : String,
    var courseRegion : ArrayList<String>,
    var courseTime : Int,
    var userImage : Int,
    var userName : String,
    var placeImage : Int,
    var placeName : String
) : Serializable