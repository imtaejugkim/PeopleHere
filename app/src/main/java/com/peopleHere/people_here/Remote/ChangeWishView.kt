package com.peopleHere.people_here.Remote

interface ChangeWishView {
    fun ChangeWishLoading()
    fun ChangeWishSuccess()
    fun ChangeWishFailure(status : Int, message : String)
}