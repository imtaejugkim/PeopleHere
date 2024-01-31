package com.example.people_here.Remote

import com.example.people_here.ApplicationClass

// 여기에 비동기식 통신 코드 구현
class AuthService {
    private val authService = ApplicationClass.retrofit.create(RetrofitInterface::class.java)

    // 본인 코드에서 사용할 함수 정의
//    fun xxx(singUpView : SignUpView){
//        this.signUpView = signUpView
//    }
}