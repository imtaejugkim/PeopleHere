package com.peopleHere.people_here.Data

data class CourseData (
    var courseTitle : String,
    var courseRegion : ArrayList<String>,
    var courseTime : Int,
    var courseCategory : ArrayList<String>,
    var courseQuestion : ArrayList<CourseQuestionData>,
    var courseMap : ArrayList<CoursePathData>,
    var courseReview : ArrayList<CourseReviewData>,
    var courseSchedule : ArrayList<CourseScheduleData>
)