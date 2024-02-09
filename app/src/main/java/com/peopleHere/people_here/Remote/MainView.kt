package com.peopleHere.people_here.Remote

import com.peopleHere.people_here.Data.MainData

interface MainView {
    fun MainLoading()
    fun MainSuccess(content : ArrayList<MainData>)
    fun MainFailure(code : Int, message : String)
}