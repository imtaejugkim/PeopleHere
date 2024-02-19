package com.peopleHere.people_here.Remote

interface JoinConfirmView {
    fun JoinConfirmLoading()
    fun JoinConfirmSuccess()
    fun JoinConfirmFialure(status : Int, message : String)
}