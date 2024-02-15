package com.peopleHere.people_here.Remote

interface ShowDatesView {
    fun ShowDatesLoading()
    fun ShowDateSuccess()
    fun ShowDatesFailure(code: Int, msg: String)
}