package com.peopleHere.people_here.TitleCategory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.peopleHere.people_here.ApplicationClass
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivityTitleBinding

class TitleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTitleBinding
    var next: Boolean = false
    var next:Boolean=false
    var time = 0
    var text : String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTitleBinding.inflate(layoutInflater)
        binding.btnNext.setOnClickListener {
            if (next) {

                ApplicationClass.ptourName=binding.etIntroduce.text.toString()//

                Log.d("APP_ptourname",ApplicationClass.ptourName.toString())
                val intent = Intent(this, CategoryActivity::class.java)
                text = binding.etIntroduce.text.toString()
                intent.putExtra("text",text)
                startActivity(intent)
            }
            //여기서 유형 띄우면 된다
        }

        time = intent.getIntExtra("time",0)

        ButtonOn()
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            finish()
        }
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
                    next = true
                } else {//클릭 불가능 하게도 설정하기
                    next = false
                    binding.btnNext.setBackgroundResource(R.drawable.making_tour_button_close)//설정 회색으로
                }



                binding.tvCountNum.setText("${binding.etIntroduce.length()} /30")
            }

        })
    }
}