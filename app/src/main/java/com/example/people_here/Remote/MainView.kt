package com.example.people_here.Remote

import com.example.people_here.Data.MainData

interface MainView {
    fun MainLoading()
    fun MainSuccess(mainDataList : List<MainData>)
}