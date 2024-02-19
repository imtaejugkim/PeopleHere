package com.peopleHere.people_here.Remote

import com.peopleHere.people_here.Data.MainData

interface AddTourDateView {
    fun AddTourDateLoading()
    fun AddTourDateSuccess()
    fun AddTourDateFailure(status : Int, message : String)
}