package com.peopleHere.people_here.Remote

interface BringUserView {
    fun BringUserViewLoading()
    fun BringUserViewSuccess(content : BringUserResponse)
    fun BringUserViewFailure(status : Int, message : String)
}