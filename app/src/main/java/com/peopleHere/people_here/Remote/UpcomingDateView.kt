package com.peopleHere.people_here.Remote

import com.peopleHere.people_here.Data.MainData

interface UpcomingDateView {
    fun UpcomingDateLoading()
    fun UpcomingDateSuccess(content : ArrayList<UpcomingDateResponse>)
    fun UpcomingDateFailure(status : Int, message : String)
}