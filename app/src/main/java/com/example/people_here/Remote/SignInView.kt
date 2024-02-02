package com.example.people_here.Remote

interface SignInView {
    fun SignInLoading()
    fun SignInSuccess()
    fun SignInFailure(code: Int, msg: String)
}