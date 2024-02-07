package com.peopleHere.people_here.Remote

import com.peopleHere.people_here.ApplicationClass

// 여기에 비동기식 통신 코드 구현
class AuthService {
    private val authService = ApplicationClass.retrofit.create(RetrofitInterface::class.java)
    private lateinit var mainView : MainView

    // 본인 코드에서 사용할 함수 정의
//    fun xxx(singUpView : SignUpView){
//        this.signUpView = signUpView
//    }

    fun setMainView(mainView: MainView){
        this.mainView = mainView
    }

//    fun mainInfo(){
//        mainView.MainLoading()
//        authService.mainInfo().enqueue(object : Callback<BaseResponse<MainResponse>>{
//            override fun onResponse(
//                call: Call<BaseResponse<MainResponse>>,
//                response: Response<BaseResponse<MainResponse>>
//            ) {
//                Log.d("response", response.toString())
//                if(response.isSuccessful){
//                    val resp = response.body()
//                    Log.d("Main Response Body", resp.toString())
//                    Log.d("Main Response Body result", resp?.result.toString())
//                    when(resp!!.code){
//                        200 -> mainView.MainSuccess()
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<BaseResponse<MainResponse>>, t: Throwable) {
//                Log.d("User Failed", "User Failed")
//            }
//
//        })
//    }
}