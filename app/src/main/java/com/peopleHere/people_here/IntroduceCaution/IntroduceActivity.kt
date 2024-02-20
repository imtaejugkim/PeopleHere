package com.peopleHere.people_here.IntroduceCaution

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.peopleHere.people_here.MainActivity
import com.peopleHere.people_here.MakingTour.MakingTourCourseFinishActivity
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivityIntroduceBinding

class IntroduceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroduceBinding
    var time = 0
    var text : String ?= null
    private var categoryNames : ArrayList<String> = arrayListOf()
    var introduce : String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        time = intent.getIntExtra("time",0)
        text = intent.getStringExtra("text")
        categoryNames = intent.getStringArrayListExtra("categoryNames")!!

        binding = ActivityIntroduceBinding.inflate(layoutInflater)
        ButtonOn()
        binding.btnNext.setOnClickListener {
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



        setContentView(binding.root)

    }



    private fun ButtonOn() {
        binding.etIntroduce.addTextChangedListener(object : TextWatcher {
            var maxtext=""
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


