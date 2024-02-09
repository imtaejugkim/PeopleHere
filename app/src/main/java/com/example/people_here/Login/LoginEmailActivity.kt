package com.example.people_here.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.people_here.SignUp.SignUpActivity
import com.example.people_here.databinding.ActivityLoginEmailBinding

class LoginEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginEmailBinding
    private var checkContinue: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        ButtonOn()
        binding.cvContinue.setOnClickListener {

            if (checkContinue) {
                //여기서 server에서 받아서 false면 signUp으로 가고 true면 LoginEmailNext로 보내기

                val intent = Intent(this, SignUpActivity::class.java)
                intent.putExtra("email", binding.etEmail.text.toString())//이메일 보내기
                startActivity(intent)
                /*
                    val intent = Intent(this, LoginEmailNextActivity::class.java)
                    intent.putExtra("email",binding.etEmail.text.toString())//이메일 보내기
                    startActivity(intent)*/
            }
        }
        setContentView(binding.root)

    }

    private fun ButtonOn() {
        binding.etEmail.addTextChangedListener(object : TextWatcher {
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

                if (editable.length > 0) {//6자면 검정색

                    binding.cvContinue.setBackgroundResource(com.example.people_here.R.drawable.making_tour_button_next)
                    checkContinue = true
                } else {//클릭 불가능 하게도 설정하기
                    binding.cvContinue.setBackgroundResource(com.example.people_here.R.drawable.making_tour_button_close)//설정 회색으로
                    checkContinue = false
                }
            }
        })
    }
}