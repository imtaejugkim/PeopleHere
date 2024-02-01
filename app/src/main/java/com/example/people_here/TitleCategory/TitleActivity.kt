package com.example.people_here.TitleCategory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.people_here.AddPicture.CustomAlbumActivity
import com.example.people_here.R
import com.example.people_here.databinding.ActivityAddPictureBinding
import com.example.people_here.databinding.ActivityTitleBinding

class TitleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTitleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityTitleBinding.inflate(layoutInflater)
        binding.btnNext.setOnClickListener {

            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
            //여기서 유형 띄우면 된다
        }
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
                binding.tvCountNum.setText("${binding.etIntroduce.length()} /30")
            }
        })
    }
}