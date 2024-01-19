package com.example.people_here.IntroduceCaution

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.people_here.R
import com.example.people_here.databinding.ActivityIntroduceBinding

class IntroduceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroduceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroduceBinding.inflate(layoutInflater)
        ButtonOn()
        binding.btnNext.setOnClickListener {
        //TODO: 여기 넘어가는거 막음
        /*    val intent = Intent(this, CautionActivity::class.java)//화면전환
            startActivity(intent)
        */}



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


