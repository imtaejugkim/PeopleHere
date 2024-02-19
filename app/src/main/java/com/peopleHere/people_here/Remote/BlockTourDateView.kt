package com.peopleHere.people_here.Remote

interface BlockTourDateView {
    fun BlockTourDateLoading()
    fun BlockTourDateSuccess()
    fun BlockTourDateFailure(status : Int, message : String)
}