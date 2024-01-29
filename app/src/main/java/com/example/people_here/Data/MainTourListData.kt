package com.example.people_here.Data

data class MainTourListData (
    var mainTourListTitle : String,
    var mainTourListTime : String,
    var mainTourListRegion : ArrayList<String>,
    var mainTourListCourses: ArrayList<MainTourListCourseData>,
)