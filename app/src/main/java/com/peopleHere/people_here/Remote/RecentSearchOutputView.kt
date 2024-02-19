package com.peopleHere.people_here.Remote

interface RecentSearchOutputView {
    fun RecentSearchOutputViewLoading()
    fun RecentSearchOutputViewSuccess(content : ArrayList<RecentSearchResponse>)
    fun RecentSearchOutputViewFailure(status : Int, message : String)
}