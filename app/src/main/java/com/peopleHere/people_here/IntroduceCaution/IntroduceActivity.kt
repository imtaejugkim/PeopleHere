package com.peopleHere.people_here.IntroduceCaution

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import androidx.core.content.ContentProviderCompat.requireContext
import com.peopleHere.people_here.ApplicationClass
import com.peopleHere.people_here.Data.PlaceData
import com.peopleHere.people_here.Data.PlacesImageData

import com.peopleHere.people_here.MainActivity

import com.peopleHere.people_here.MakingTour.MakingTourCourseFinishActivity
import com.peopleHere.people_here.R
import com.peopleHere.people_here.Remote.AuthService
import com.peopleHere.people_here.databinding.ActivityIntroduceBinding

class IntroduceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroduceBinding
    private lateinit var authService: AuthService
    var time = 0
    var text : String ?= null
    private var categoryNames : ArrayList<String> = arrayListOf()
    var introduce : String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        time = intent.getIntExtra("time",0)
        text = intent.getStringExtra("text")

        binding = ActivityIntroduceBinding.inflate(layoutInflater)
        ButtonOn()
        authService = AuthService(this)

        ApplicationClass.ptourContent = "시험텍스트"
        Log.d("APP_ptourContent",ApplicationClass.ptourContent.toString())

        if (ApplicationClass.pencodingString != null && ApplicationClass.poriginalFileName != null) {
            for (i in 0 until ApplicationClass.pencodingString!!.size) {
                ApplicationClass.pplaceImage?.add(
                    PlacesImageData(
                        encodingString = ApplicationClass.pencodingString!![i],
                        originalFileName = ApplicationClass.poriginalFileName!![i]
                    )
                )
            }
        }else{
          Log.d("APP_ERR","여기가 빔 흠 ")
            //OK
        }

        for (i in 0 until ApplicationClass.pencodingString!!.size) {
            ApplicationClass.pplaces?.add(
                PlaceData(
                    ApplicationClass.pplaceName!![i],
                    mutableListOf(ApplicationClass.pplaceImage!![i]),
                    ApplicationClass.pplaceAddress!![i],
                    ApplicationClass.platLng!![i],
                    i
                )
            )
        }


        binding.btnNext.setOnClickListener {

            authService.postNewTourLast(
                ApplicationClass.puserId!!,
                ApplicationClass.ptourName!!,
                ApplicationClass.ptourTime!!,
                ApplicationClass.ptourContent!!,
                ApplicationClass.pcategoryNames!!,
                ApplicationClass.pplaces!!
            )

            val intent = Intent(this, MakingTourCourseFinishActivity::class.java)//화면전환
            introduce = binding.etIntroduce.text.toString()
            intent.putExtra("time",time)
            intent.putExtra("text",text)
            intent.putExtra("categoryNames",categoryNames)
            intent.putExtra("introduce",introduce)
            startActivity(intent)


        }

        binding.ivCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)//화면전환
            startActivity(intent)
            finish()
        }




        binding.btnBack.setOnClickListener {
            finish()
        }


        setContentView(binding.root)
    }


    private fun ButtonOn() {
        binding.etIntroduce.addTextChangedListener(object : TextWatcher {
            var maxtext = ""
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }


            override fun afterTextChanged(editable: Editable) {
                if (editable.length > 0) {//한글자 이상이면 검정색
                    binding.btnNext.setBackgroundResource(R.drawable.making_tour_button_next)
                } else {//클릭 불가능 하게도 설정하기
                    binding.btnNext.setBackgroundResource(R.drawable.making_tour_button_close)//설정 회색으로
                }
                binding.tvCountNum.setText("${binding.etIntroduce.length()} /500")
            }
        })
    }
}


