package com.example.people_here.Data

data class MainData (
    var mainTourListTitle : String,
    var mainTourListTime : String,
    var mainTourHeart : Boolean,
    var mainTourListRegion : ArrayList<String>,
    var mainTourListCourses: ArrayList<MainCourseData>,
)