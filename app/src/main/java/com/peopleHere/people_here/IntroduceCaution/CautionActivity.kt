package com.peopleHere.people_here.IntroduceCaution

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.peopleHere.people_here.MainActivity
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivityCautionBinding

class CautionActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCautionBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityCautionBinding.inflate(layoutInflater)
        ButtonOn()
        binding.btnNext.setOnClickListener {
        //TODO:이것도 막음
        /*
            val intent = Intent(this, AddPictureActivity::class.java)//화면전환
            startActivity(intent)
*/


        }

        binding.ivCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
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