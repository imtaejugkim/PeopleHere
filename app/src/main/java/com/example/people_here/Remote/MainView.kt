package com.example.people_here.Remote

import com.example.people_here.Data.MainData

interface MainView {
    fun MainLoading()
    fun MainSuccess(content : ArrayList<MainData>)
    fun MainFailure(code : Int, message : String)
}