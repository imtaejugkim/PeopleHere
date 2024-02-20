package com.peopleHere.people_here

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.peopleHere.people_here.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.peopleHere.people_here.Main.MainFragment
import com.peopleHere.people_here.MakingTour.OnBoardingActivity
import com.peopleHere.people_here.Profile.ProfileFirstFragment
import com.peopleHere.people_here.Profile.ProfileFragment
import com.peopleHere.people_here.MyTour.MakingCourseActivity
import com.peopleHere.people_here.TitleCategory.MakingTourFragment
import com.peopleHere.people_here.Message.MessageFragment
import com.peopleHere.people_here.Message.MessageLoggedFragment
import com.peopleHere.people_here.Profile.DayTripManageActivity
import com.peopleHere.people_here.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("token_check", X_ACCESS_TOKEN)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var checktoken = ApplicationClass.mSharedPreferencesManager.getString("token", "null")

        Log.d("checktoken",checktoken.toString())
        var alarmAvailable = intent.getStringExtra("ok")
        if (alarmAvailable == "alarm") {
            val toastLayout = LayoutInflater.from(binding.root.context)
                .inflate(
                    R.layout.toast_alarm,
                    null
                ) // R.layout.custom_toast_layout은 사용자가 정의한 레이아웃 파일입니다.
            val toast = Toast(binding.root.context)
            toast.view = toastLayout
            toast.setGravity(Gravity.BOTTOM, 0, 80.dpToPx()) // 80dp 아래로
            toast.show()
        }
        binding.navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MainFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_wish -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MakingTourFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_making_course -> {//코스 만들기 고쳤다아
                    if(checktoken == "null"){
                        val intent = Intent(this, OnBoardingActivity::class.java)
                        startActivity(intent)
                        return@setOnItemSelectedListener true
                    }else{
                        val intent = Intent(this, MakingCourseActivity::class.java)
                        startActivity(intent)
                        return@setOnItemSelectedListener true
                    }
                }

                R.id.menu_message -> {//
                    if (checktoken == "null") {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, MessageFragment()).commit()
                        return@setOnItemSelectedListener true

                    } else {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, MessageLoggedFragment()).commit()
                        return@setOnItemSelectedListener true
                    }
                }

                /*
                                R.id.menu_profile -> {//코스 만들기 고쳤다아
                                    val intent = Intent(this, DayTripManageActivity::class.java)
                                    startActivity(intent)
                                    return@setOnItemSelectedListener true
                                }
                */


                R.id.menu_profile -> {
                    if (checktoken == "null") {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ProfileFirstFragment()).commit()
                        return@setOnItemSelectedListener true

                    } else {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ProfileFragment()).commit()
                        return@setOnItemSelectedListener true

                    }
                }



//                R.id.menu_profile -> {
//                    if(X_ACCESS_TOKEN=="Authorization"){
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.main_frm, ProfileFirstFragment()).commit()
//                        return@setOnItemSelectedListener true
//
//                    }else{
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.main_frm, ProfileFragment()).commit()
//                        return@setOnItemSelectedListener true
//
//                    }
//                }

                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }
        binding.navigation.selectedItemId = R.id.menu_home
    }

    fun Int.dpToPx(): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (this * scale).toInt()
    }

}
