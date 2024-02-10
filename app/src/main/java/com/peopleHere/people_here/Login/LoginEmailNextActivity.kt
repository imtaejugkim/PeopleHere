package com.peopleHere.people_here.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import com.example.people_here.databinding.ActivityLoginEmailNextBinding

class LoginEmailNextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginEmailNextBinding
    private var checkContinue:Boolean=false
    private var seeClicked:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginEmailNextBinding.inflate(layoutInflater)
        ButtonOn()
        var receviedIntent=intent
        var userEmail=receviedIntent.getStringExtra("email")//이메일 갖와서 연동
        binding.tvEmailUser.text = userEmail//이메일 세팅
        binding.tvSee.setOnClickListener {
            if(seeClicked){
                binding.etPassword.inputType= InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                seeClicked=false
            }else{
                Log.d("ehtlep","false")
                binding.etPassword.inputType= InputType.TYPE_TEXT_VARIATION_PASSWORD
                seeClicked=true
            }
        }
        binding.cvContinue.setOnClickListener {
            if(checkContinue){
                //TODO: 서버에 보내서 없으면 회원가입 처리?? or 어떻게
            /*    val intent = Intent(this, LoginEmailNextActivity::class.java)
                startActivity(intent)
            */
            }
        }
        setContentView(binding.root)
    }

    private fun ButtonOn() {
        binding.etPassword.addTextChangedListener(object : TextWatcher {
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

                if (editable.length >=8) {//8자면 이상이여야 하니 ㅇㅇ
                    binding.cvContinue.setBackgroundResource(com.example.people_here.R.drawable.making_tour_button_next)
                    checkContinue= true
                } else {//클릭 불가능 하게도 설정하기
                    binding.cvContinue.setBackgroundResource(com.example.people_here.R.drawable.making_tour_button_close)//설정 회색으로
                    checkContinue=false
                }
            }
        })
    }
}