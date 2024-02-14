package com.peopleHere.people_here.Remote

import com.peopleHere.people_here.Data.MainData

interface CourseContentsView {
    fun CourseContentsLoading()
    fun CourseContentsSuccess(content : CourseContentsResponse)
    fun CourseContentsFailure(status : Int, message : String)
}