package com.peopleHere.people_here.Remote

import com.peopleHere.people_here.Data.MainData

interface RecentSearchView {
    fun RecentSearchLoading()
    fun RecentSearchSuccess()
    fun RecentSearchFailure(status : Int, message : String)
}