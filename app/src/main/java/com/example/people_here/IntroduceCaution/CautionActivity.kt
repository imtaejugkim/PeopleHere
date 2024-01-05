package com.example.people_here.IntroduceCaution

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.people_here.R
import com.example.people_here.databinding.ActivityCautionBinding

class CautionActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCautionBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityCautionBinding.inflate(layoutInflater)
        ButtonOn()
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