package com.peopleHere.people_here.Remote

interface BringCourseView {
    fun BringCourseLoading()
    fun BringCourseSuccess(content : ArrayList<BringCourseResponse>)
    fun BringCourseFailure(status : Int, message : String)
}