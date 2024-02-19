package com.peopleHere.people_here.Remote

import android.content.Context
import android.content.Intent
import android.util.Log
import com.peopleHere.people_here.ApplicationClass
import com.peopleHere.people_here.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.peopleHere.people_here.Data.ChatData
import com.peopleHere.people_here.Data.ProfileData
import com.peopleHere.people_here.Local.getJwt
import com.peopleHere.people_here.Local.saveJwt
import com.peopleHere.people_here.Login.LoginEmailNextActivity
import com.peopleHere.people_here.Login.LoginPhoneNextActivity
import com.peopleHere.people_here.Login.VerifyPhoneActivity
import com.peopleHere.people_here.MainActivity
import com.peopleHere.people_here.SignUp.SignUpActivity
import okhttp3.Interceptor
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 여기에 비동기식 통신 코드 구현
interface CheckEmailCallback {
    fun onEmailAvailable(isAvailable: Boolean)
}

class AuthService(private val context: Context)  {

    private val authService = ApplicationClass.retrofit.create(RetrofitInterface::class.java)

    private lateinit var mainView: MainView

    private var checkEmailCallback: CheckEmailCallback? = null
    private lateinit var courseContentsView: CourseContentsView
    private lateinit var upcomingDateView: UpcomingDateView
    private lateinit var bringCourseView: BringCourseView
    private var changeWishView: ChangeWishView?= null
    private lateinit var requestEnjoyView: RequestEnjoyView
    private lateinit var joinConfirmView: JoinConfirmView
    private lateinit var profileView: ProfileView
    private lateinit var messageView: MessageView
    private lateinit var addTourDateView: AddTourDateView
    private lateinit var bringUserView: BringUserView

    // 본인 코드에서 사용할 함수 정의
//    fun xxx(singUpView : SignUpView){
//        this.signUpView = signUpView
//    }
    private lateinit var signInView: SignInView//로그인
    fun setCheckEmailCallback(callback: CheckEmailCallback) {
        this.checkEmailCallback = callback
    }

    fun setSignInView(signInView: SignInView) {//아직 초기화가 안됐음 따라서 activity에서 해주기
        this.signInView = signInView//signupVIew 사용 하기 위해
    }



    fun signin(id: String, pw: String) {
        val request = SignInRequest(id, pw)//email이랑 pw를 넘기겠지
        authService.singin(request)
            .enqueue(object : Callback<BaseResponse<SignInResponse>> {
                //익명이니 함수 구현 해줘야 함+enqueue 로 자동 비동기
                override fun onResponse(
                    call: Call<BaseResponse<SignInResponse>>,
                    response: Response<BaseResponse<SignInResponse>>
                ) {
                    Log.d("Response_signIn", response.toString())
                    val resp = response.body()
                    if (resp != null) {
                        when (resp.status) {
                            200 -> {
                                val signInResponse = resp.result
                                signInResponse?.let {
                                    signInView.SignInSuccess()
                                    Log.d(
                                        "AuthService",
                                        "Nickname: ${resp.result.userId}, Token: ${resp.result.jwtToken.accessToken}"
                                    )
                                    saveJwt(resp.result.jwtToken.accessToken)





                                    val intent = Intent(context, MainActivity::class.java)
                                    //intent.putExtra("email", binding.etEmail.text.toString())
                                    context.startActivity(intent)
                                }
                            }

                            else -> signInView.SignInFailure()
                        }
                    } else {
                        Log.e("AuthService", "Response body is null.")
                        signInView.SignInFailure()
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<SignInResponse>>,
                    t: Throwable
                ) {
                    Log.d("SignIn failed", t.toString())//실패했을때 어떤 이윤지 쓰로어블하게
                }
            })
    }

    fun signinPhone(phoneNumber: String, pw: String) {
        val request = SignInPhoneRequest(phoneNumber, pw)//email이랑 pw를 넘기겠지
        authService.signinPhone(request)
            .enqueue(object : Callback<BaseResponse<SignInPhoneResponse>> {
                //익명이니 함수 구현 해줘야 함+enqueue 로 자동 비동기
                override fun onResponse(
                    call: Call<BaseResponse<SignInPhoneResponse>>,
                    response: Response<BaseResponse<SignInPhoneResponse>>
                ) {
                    //TODO: 안 되니까 SignUP구현하고 다시 해보기
                    Log.d("Response_signIn", response.toString())

                    val resp = response.body()
                    if (resp != null) {
                        when (resp.status) {
                            200 -> {
                                val signInResponse = resp.result
                                signInResponse?.let {
                                    signInView.SignInSuccess()
                                    Log.d(
                                        "AuthService",
                                        "Nickname: ${resp.result.userId}, Token: ${resp.result.jwtToken.accessToken}"
                                    )
                                    //TODO:토큰 이제 바뀌게
                                    //TODO: 회원가입 할때도??

                                    Log.d("Check_token", X_ACCESS_TOKEN)
                                    val intent = Intent(context, MainActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }

                            else -> signInView.SignInFailure()
                        }
                    } else {
                        Log.e("AuthService", "Response body is null.")
                        signInView.SignInFailure()
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<SignInPhoneResponse>>,
                    t: Throwable
                ) {
                    Log.d("SignIn failed", t.toString())//실패했을때 어떤 이윤지 쓰로어블하게
                }
            })
    }


    fun setMainView(mainView: MainView) {
        this.mainView = mainView
    }

    fun MessageView(messageView: MessageView) {
        this.messageView = messageView
    }

    fun setCourseContentsView(courseContentsView: CourseContentsView) {
        this.courseContentsView = courseContentsView
    }

    fun setUpcomingDateView(upcomingDateView: UpcomingDateView) {
        this.upcomingDateView = upcomingDateView
    }

    fun setBringCourseView(bringCourseView: BringCourseView) {
        this.bringCourseView = bringCourseView
    }

    fun setChangeWishView(changeWishView: ChangeWishView) {
        this.changeWishView = changeWishView
    }

    fun setRequestEnjoyView(requestEnjoyView: RequestEnjoyView) {
        this.requestEnjoyView = requestEnjoyView
    }

    fun setJoinConfirmView(joinConfirmView: JoinConfirmView) {
        this.joinConfirmView = joinConfirmView
    }

    fun ProfileView(profileView: ProfileView) {
        this.profileView = profileView
    }

    fun setAddTourDateView(addTourDateView: AddTourDateView) {
        this.addTourDateView = addTourDateView
    }

    fun sertBringUserView(bringUserView: BringUserView) {
        this.bringUserView = bringUserView
    }

    fun mainInfo() {
//        mainView.MainLoading()
        authService.mainInfo(0, 10, listOf("createdAt,desc"))
            .enqueue(object : Callback<BaseResponse<MainResponse>> {
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

    fun checkEmail(email: String) {
        authService.checkEmail(email)
            .enqueue(object : Callback<BaseResponse<CheckEmailResponse>> {
                override fun onResponse(
                    call: Call<BaseResponse<CheckEmailResponse>>,
                    response: Response<BaseResponse<CheckEmailResponse>>
                ) {
                    val resp = response.body()
                    Log.d("response_error_checkemail", response.message())
                    resp?.let {
                        when (it.status) {
                            200 -> {
                                // 성공적인 응답 처리
                                val checkEmailResponse = it.result
                                checkEmailResponse?.let { response ->
                                    if (response.emailAvailable) {
                                        val intent = Intent(context, SignUpActivity::class.java)
                                        //intent.putExtra("email", binding.etEmail.text.toString())
                                        context.startActivity(intent)
                                    } else {
                                        val intent =
                                            Intent(context, LoginEmailNextActivity::class.java)
                                        //intent.putExtra("email", binding.etEmail.text.toString())
                                        context.startActivity(intent)
                                    }
                                    Log.d("CheckEmail", "qewewq")
                                    checkEmailCallback?.onEmailAvailable(response.emailAvailable)
                                }
                            }

                            else -> {
                                Log.d(
                                    "checkEmail_fail",
                                    "fail"
                                )
                            }
                        }
                    } ?: run {
                        Log.e("checkEmail", "Response body is null")

                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<CheckEmailResponse>>,
                    t: Throwable
                ) {
                    // 네트워크 실패 처리
                    Log.e("checkEmail", "Network request failed", t)
                }
            })
    }

    fun checkPhoneNumber(phoneNumber: String) {
        authService.checkPhoneNumber(phoneNumber)
            .enqueue(object : Callback<BaseResponse<CheckPhoneNumberResponse>> {
                override fun onResponse(
                    call: Call<BaseResponse<CheckPhoneNumberResponse>>,
                    response: Response<BaseResponse<CheckPhoneNumberResponse>>
                ) {
                    val resp = response.body()

                    resp?.let {
                        when (it.status) {
                            200 -> {
                                // 성공적인 응답 처리
                                val checkEmailResponse = it.result
                                checkEmailResponse?.let { response ->
                                    if (response.phoneNumberAvailable) {
                                        //기존에 없던것
                                        val intent =
                                            Intent(context, VerifyPhoneActivity::class.java)
                                        context.startActivity(intent)
                                    } else {
                                        val intent =
                                            Intent(context, LoginPhoneNextActivity::class.java)
                                        //intent.putExtra("email", binding.etEmail.text.toString())
                                        context.startActivity(intent)
                                    }
                                    checkEmailCallback?.onEmailAvailable(response.phoneNumberAvailable)
                                }
                            }

                            else -> {
                                Log.d(
                                    "checkEmail_fail",
                                    "fail"
                                )
                            }
                        }
                    } ?: run {
                        Log.e("checkEmail", "Response body is null")

                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<CheckPhoneNumberResponse>>,
                    t: Throwable
                ) {
                    // 네트워크 실패 처리
                    Log.e("checkEmail", "Network request failed", t)
                }
            })
    }

    fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        birth: String,
        gender: String,
        marketingConsent: Boolean
    ) {
        val request = SignUpRequest(
            email,
            password,
            firstName,
            lastName,
            birth,
            gender,
            marketingConsent
        )//email이랑 pw를 넘기겠지
        authService.signup(request)
            .enqueue(object : Callback<BaseResponse<SignUpResponse>> {
                //익명이니 함수 구현 해줘야 함+enqueue 로 자동 비동기
                override fun onResponse(
                    call: Call<BaseResponse<SignUpResponse>>,
                    response: Response<BaseResponse<SignUpResponse>>
                ) {
                    Log.d("response_e", email)
                    Log.d("response_p", password)
                    Log.d("response_f", firstName)
                    Log.d("response_l", lastName)
                    Log.d("response_g", gender)
                    Log.d("response_b", birth)
                    Log.d("response_signup", response.toString())
                    val resp = response.body() ?: run {
                        Log.e("Signup response", "Response body is null")
                        return
                    }
                    when (resp.status) {
                        200 -> {
                            val signUpResponse = resp.result
                            signUpResponse?.let {
                                Log.d("Response_success", resp.result.userId.toString())
                            }
                        }


                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<SignUpResponse>>,
                    t: Throwable
                ) {
                    Log.d("SignIn failed", t.toString())//실패했을때 어떤 이윤지 쓰로어블하게
                }
            })
    }

    fun signupPhone(
        email: String,
        phoneNumber: String,
        password: String,
        firstName: String,
        lastName: String,
        birth: String,
        gender: String,
        marketingConsent: Boolean
    ) {
        val request = SignUpPhoneRequest(
            email,
            phoneNumber,
            password,
            firstName,
            lastName,
            birth,
            gender,
            marketingConsent
        )//email이랑 pw를 넘기겠지
        authService.signupPhone(request)
            .enqueue(object : Callback<BaseResponse<SignUpPhoneResponse>> {
                //익명이니 함수 구현 해줘야 함+enqueue 로 자동 비동기
                override fun onResponse(
                    call: Call<BaseResponse<SignUpPhoneResponse>>,
                    response: Response<BaseResponse<SignUpPhoneResponse>>
                ) {
                    Log.d("response_e", email)
                    Log.d("response_ph", phoneNumber)
                    Log.d("response_p", password)
                    Log.d("response_f", firstName)
                    Log.d("response_l", lastName)
                    Log.d("response_g", gender)
                    Log.d("response_b", birth)
                    Log.d("response_signup", response.toString())
                    val resp = response.body() ?: run {
                        Log.e("Signup response", "Response body is null")
                        return
                    }
                    when (resp.status) {
                        200 -> {
                            val signUpResponse = resp.result
                            signUpResponse?.let {
                                Log.d("Response_success", resp.result.userId.toString())
                            }
                        }
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<SignUpPhoneResponse>>,
                    t: Throwable
                ) {
                    Log.d("SignIn failed", t.toString())//실패했을때 어떤 이윤지 쓰로어블하게
                }
            })
    }

    fun courseContentsInfo(tourId: Int) {
//        mainView.MainLoading()
        authService.courseContentsInfo(tourId)
            .enqueue(object : Callback<BaseResponse<CourseContentsResponse>> {
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
                            else -> courseContentsView.CourseContentsFailure(
                                resp.status,
                                resp.message
                            )
                        }
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<CourseContentsResponse>>,
                    t: Throwable
                ) {
                    Log.d("CourseContents Failed", t.toString())
                }

            })
    }

    fun upcomingDateInfo(tourId: Int) {
//        mainView.MainLoading()
        authService.upcomingDateInfo(tourId)
            .enqueue(object : Callback<BaseResponse<ArrayList<UpcomingDateResponse>>> {
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
                            else -> upcomingDateView.UpcomingDateFailure(
                                resp.status,
                                resp.message
                            )
                        }
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<ArrayList<UpcomingDateResponse>>>,
                    t: Throwable
                ) {
                    Log.d("Upcoming Failed", t.toString())
                }

            })
    }

    fun bringCourseInfo(id: Int, option: String) {
//        mainView.MainLoading()
        authService.bringCourseInfo(id, option)
            .enqueue(object : Callback<BaseResponse<ArrayList<BringCourseResponse>>> {
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
                            else -> bringCourseView.BringCourseFailure(
                                resp.status,
                                resp.message
                            )
                        }
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<ArrayList<BringCourseResponse>>>,
                    t: Throwable
                ) {
                    Log.d("Upcoming Failed", t.toString())
                }

            })
    }

    fun changeWishInfo(tourId: Int) {
//        mainView.MainLoading()
        authService.changeWishInfo(tourId)
            .enqueue(object : Callback<BaseResponse<ChangeWishResponse>> {
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
                            200 -> changeWishView?.ChangeWishSuccess()
                            else -> changeWishView?.ChangeWishFailure(resp.status, resp.message)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<ChangeWishResponse>>,
                    t: Throwable
                ) {
                    Log.d("Upcoming Failed", t.toString())
                }

            })
    }

    fun profileInfo() {
        authService.ProfileInfo()
            .enqueue(object : Callback<BaseResponse<ProfileData>> {
                override fun onResponse(
                    call: Call<BaseResponse<ProfileData>>,
                    response: Response<BaseResponse<ProfileData>>
                ) {
                    Log.d("profile response", response.toString())
                    if (response.isSuccessful) {
                        val resp = response.body()
                        when (resp!!.status) {
                            200 -> profileView.ProfileSuccess(resp.result)//여기로 프로필 데이터받음
                            else -> profileView.MainFailure(
                                resp.status,
                                resp.message
                            )
                        }

                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<ProfileData>>,
                    t: Throwable
                ) {
                    Log.d("Upcoming Failed", t.toString())
                }

            })
    }
    fun chatUpdate(tourId: Int) {
        authService.ChatUpdate(tourId)
            .enqueue(object : Callback<BaseResponse<ArrayList<ChatData>>> {
                override fun onResponse(
                    call: Call<BaseResponse<ArrayList<ChatData>>>,
                    response: Response<BaseResponse<ArrayList<ChatData>>>
                ) {
                    Log.d("tellmewhy response", response.toString())

                    if (response.isSuccessful) {
                        val resp = response.body()
                        Log.d("BringCourse Response Body", resp.toString())
                        Log.d("BringCourse Response Body result", resp?.result.toString())
                        when (resp!!.status) {
                            200 -> messageView.MessageSuccess(resp.result)//여기로 프로필 데이터받음
                            else -> messageView.MessageFailure(
                                resp.status,
                                resp.message
                            )
                        }

                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<ArrayList<ChatData>>>,
                    t: Throwable
                ) {
                    Log.d("Upcoming Failed", t.toString())
                }

            })
    }

    fun changePassword(password: String) {
        authService.changePassword(password)
            .enqueue(object : Callback<ChangePasswordResponse> {
                override fun onResponse(
                    call: Call<ChangePasswordResponse>,
                    response: Response<ChangePasswordResponse>
                ) {
                    Log.d("password_changed_res", response.toString())
                    val resp = response.body()
                    resp?.let {
                        when (it.status) {
                            200 -> {
                                // 성공적인 응답 처리
                                Log.d("password_changed", it.success.toString())
                            }

                            else -> {
                                Log.d(
                                    "checkEmail_fail",
                                    "fail"
                                )
                            }
                        }
                    } ?: run {
                        Log.e("password_changed", "Response body is null")

                    }
                }

                override fun onFailure(
                    call: Call<ChangePasswordResponse>,
                    t: Throwable
                ) {
                    // 네트워크 실패 처리
                    Log.e("checkEmail", "Network request failed", t)
                }
            })

    }

    fun requestEnjoyInfo(tourDateId: Int) {
//        mainView.MainLoading()
        authService.requestEnjoyInfo(tourDateId)
            .enqueue(object : Callback<BaseResponse<RequestEnjoyResponse>> {
                override fun onResponse(
                    call: Call<BaseResponse<RequestEnjoyResponse>>,
                    response: Response<BaseResponse<RequestEnjoyResponse>>
                ) {
//                    Log.d("RequestEnjoy response", response.toString())
                    if (response.isSuccessful) {
                        val resp = response.body()
//                        Log.d("RequestEnjoy Response Body", resp.toString())
//                        Log.d("RequestEnjoy Response Body result", resp?.result.toString())
                        when (resp!!.status) {
                            200 -> requestEnjoyView.RequestEnjoySuccess(resp.result)
                            else -> requestEnjoyView.RequestEnjoyFailure(resp.status, resp.message)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<RequestEnjoyResponse>>,
                    t: Throwable
                ) {
                    Log.d("Upcoming Failed", t.toString())
                }

            })
    }

    fun joinConfirmInfo(tourDateId: Int) {
//        mainView.MainLoading()
        authService.joinConfirmInfo(tourDateId)
            .enqueue(object : Callback<BaseResponse<String>> {
                override fun onResponse(
                    call: Call<BaseResponse<String>>,
                    response: Response<BaseResponse<String>>
                ) {
//                    Log.d("joinConfirm response", response.toString())
                    if (response.isSuccessful) {
                        val resp = response.body()
//                        Log.d("joinConfirm Response Body", resp.toString())
//                        Log.d("joinConfirm Response Body result", resp?.result.toString())
                        when (resp!!.status) {
                            200 -> joinConfirmView?.JoinConfirmSuccess()
                            else -> joinConfirmView?.JoinConfirmFialure(resp.status, resp.message)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<String>>,
                    t: Throwable
                ) {
//                    Log.d("joinConfirm Failed", t.toString())
                }

            })
    }


    fun chatUpdate(id: Int, option: String) {
        authService.ProfileInfo()
            .enqueue(object : Callback<BaseResponse<ProfileData>> {
                override fun onResponse(
                    call: Call<BaseResponse<ProfileData>>,
                    response: Response<BaseResponse<ProfileData>>
                ) {
                    Log.d("BringCourse response", response.toString())

                    if (response.isSuccessful) {
                        val resp = response.body()
                        Log.d("BringCourse Response Body", resp.toString())
                        Log.d("BringCourse Response Body result", resp?.result.toString())
                        when (resp!!.status) {
                            200 -> profileView.ProfileSuccess(resp.result)//여기로 프로필 데이터받음
                            else -> profileView.MainFailure(
                                resp.status,
                                resp.message
                            )
                        }

                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<ProfileData>>,
                    t: Throwable
                ) {
                    Log.d("Upcoming Failed", t.toString())
                }

            })
    }

    fun addTourDateInfo(tourId: Int, date: String, time: TourTimeData?) {
//        mainView.MainLoading()
        val request = AddTourDateRequest(date,time)
        authService.addTourDateInfo(request, tourId)
            .enqueue(object : Callback<BaseResponse<String>> {
                override fun onResponse(
                    call: Call<BaseResponse<String>>,
                    response: Response<BaseResponse<String>>
                ) {
//                    Log.d("addTourDate response", response.toString())
                    if (response.isSuccessful) {
                        val resp = response.body()
//                        Log.d("addTourDate Response Body", resp.toString())
//                        Log.d("addTourDate Response Body result", resp?.result.toString())
                        when (resp!!.status) {
                            200 -> addTourDateView.AddTourDateSuccess()
                            else -> addTourDateView.AddTourDateFailure(resp.status, resp.message)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<String>>,
                    t: Throwable
                ) {
//                    Log.d("addTourDate Failed", t.toString())
                }

            })
    }

    fun bringUserInfo(userId: Int) {
//        mainView.MainLoading()
        authService.bringUserInfo(userId)
            .enqueue(object : Callback<BaseResponse<BringUserResponse>> {
                override fun onResponse(
                    call: Call<BaseResponse<BringUserResponse>>,
                    response: Response<BaseResponse<BringUserResponse>>
                ) {
//                    Log.d("bringUser response", response.toString())
                    if (response.isSuccessful) {
                        val resp = response.body()
//                        Log.d("bringUser Response Body", resp.toString())
//                        Log.d("bringUser Response Body result", resp?.result.toString())
                        when (resp!!.status) {
                            200 -> bringUserView.BringUserViewSuccess(resp.result)
                            else -> bringUserView.BringUserViewFailure(resp.status, resp.message)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<BringUserResponse>>,
                    t: Throwable
                ) {
                    Log.d("bringUser Failed", t.toString())
                }

            })
    }


}