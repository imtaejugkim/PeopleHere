package com.peopleHere.people_here.Remote

import android.util.Log
import com.peopleHere.people_here.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 여기에 비동기식 통신 코드 구현
class AuthService {
    private val authService = ApplicationClass.retrofit.create(RetrofitInterface::class.java)
    private lateinit var mainView: MainView
    private lateinit var courseContentsView: CourseContentsView
    private lateinit var upcomingDateView: UpcomingDateView
    private lateinit var bringCourseView: BringCourseView
    private lateinit var changeWishView: ChangeWishView
    // 본인 코드에서 사용할 함수 정의
//    fun xxx(singUpView : SignUpView){
//        this.signUpView = signUpView
//    }

    fun setMainView(mainView: MainView) {
        this.mainView = mainView
    }

    fun setCourseContentsView(courseContentsView: CourseContentsView){
        this.courseContentsView = courseContentsView
    }

    fun setUpcomingDateView(upcomingDateView: UpcomingDateView){
        this.upcomingDateView = upcomingDateView
    }

    fun setBringCourseView(bringCourseView: BringCourseView){
        this.bringCourseView = bringCourseView
    }

    fun setChangeWishView(changeWishView: ChangeWishView){
        this.changeWishView = changeWishView
    }

    fun mainInfo() {
//        mainView.MainLoading()
        authService.mainInfo(0, 10, listOf("createdAt,asc")).enqueue(object : Callback<BaseResponse<MainResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<MainResponse>>,
                response: Response<BaseResponse<MainResponse>>
            ) {
//                Log.d("response", response.toString())
                if (response.isSuccessful) {
                    val resp = response.body()
//                    Log.d("Main Response Body", resp.toString())
//                    Log.d("Main Response Body result", resp?.result.toString())
                    when (resp!!.status) {
                        200 -> mainView.MainSuccess(resp.result.content)
                        else -> mainView.MainFailure(resp.status, resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<MainResponse>>, t: Throwable) {
                Log.d("Main Failed", t.toString())
            }

        })
    }

    fun courseContentsInfo(tourId : Int) {
//        mainView.MainLoading()
        authService.courseContentsInfo(tourId).enqueue(object : Callback<BaseResponse<CourseContentsResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<CourseContentsResponse>>,
                response: Response<BaseResponse<CourseContentsResponse>>
            ) {
//                Log.d("response", response.toString())
                if (response.isSuccessful) {
                    val resp = response.body()
//                    Log.d("CourseContents Response Body", resp.toString())
//                    Log.d("CourseContents Response Body result", resp?.result.toString())
                    when (resp!!.status) {
                        200 -> courseContentsView.CourseContentsSuccess(resp.result)
                        else -> courseContentsView.CourseContentsFailure(resp.status, resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<CourseContentsResponse>>, t: Throwable) {
                Log.d("CourseContents Failed", t.toString())
            }

        })
    }

    fun upcomingDateInfo(tourId : Int) {
//        mainView.MainLoading()
        authService.upcomingDateInfo(tourId).enqueue(object : Callback<BaseResponse<ArrayList<UpcomingDateResponse>>> {
            override fun onResponse(
                call: Call<BaseResponse<ArrayList<UpcomingDateResponse>>>,
                response: Response<BaseResponse<ArrayList<UpcomingDateResponse>>>
            ) {
//                Log.d("Upcoming response", response.toString())
                if (response.isSuccessful) {
                    val resp = response.body()
                    Log.d("Upcoming Response Body", resp.toString())
                    Log.d("Upcoming Response Body result", resp?.result.toString())
                    when (resp!!.status) {
                        200 -> upcomingDateView.UpcomingDateSuccess(resp.result)
                        else -> upcomingDateView.UpcomingDateFailure(resp.status, resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<ArrayList<UpcomingDateResponse>>>, t: Throwable) {
                Log.d("Upcoming Failed", t.toString())
            }

        })
    }

    fun bringCourseInfo(id : Int, option : String) {
//        mainView.MainLoading()
        authService.bringCourseInfo(id, option).enqueue(object : Callback<BaseResponse<ArrayList<BringCourseResponse>>> {
            override fun onResponse(
                call: Call<BaseResponse<ArrayList<BringCourseResponse>>>,
                response: Response<BaseResponse<ArrayList<BringCourseResponse>>>
            ) {
                Log.d("BringCourse response", response.toString())
                if (response.isSuccessful) {
                    val resp = response.body()
                    Log.d("BringCourse Response Body", resp.toString())
                    Log.d("BringCourse Response Body result", resp?.result.toString())
                    when (resp!!.status) {
                        200 -> bringCourseView.BringCourseSuccess(resp.result)
                        else -> bringCourseView.BringCourseFailure(resp.status, resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<ArrayList<BringCourseResponse>>>, t: Throwable) {
                Log.d("Upcoming Failed", t.toString())
            }

        })
    }

    fun changeWishInfo(tourId: Int) {
//        mainView.MainLoading()
        authService.changeWishInfo(tourId).enqueue(object : Callback<BaseResponse<ChangeWishResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<ChangeWishResponse>>,
                response: Response<BaseResponse<ChangeWishResponse>>
            ) {
                Log.d("ChangeWish response", response.toString())
                if (response.isSuccessful) {
                    val resp = response.body()
                    Log.d("ChangeWish Response Body", resp.toString())
                    Log.d("ChangeWish Response Body result", resp?.result.toString())
                    when (resp!!.status) {
                        200 -> changeWishView.ChangeWishSuccess()
                        else -> changeWishView.ChangeWishFailure(resp.status, resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<ChangeWishResponse>>, t: Throwable) {
                Log.d("Upcoming Failed", t.toString())
            }

        })
    }
}