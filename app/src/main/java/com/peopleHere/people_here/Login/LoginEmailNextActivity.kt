package com.peopleHere.people_here.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.TextView
import com.peopleHere.people_here.ApplicationClass
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivityLoginEmailNextBinding
import java.util.regex.Pattern

class LoginEmailNextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginEmailNextBinding
    private var checkContinue: Boolean = false
    private var seeClicked: Boolean = false
    lateinit var questionPass: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailNextBinding.inflate(layoutInflater)
        ButtonOn()
        var userEmail = ApplicationClass.mSharedPreferencesManager.getString("email",null)
        binding.tvEmailUser.text = userEmail//이메일 세팅
        binding.tvSee.setOnClickListener {
            if (seeClicked) {
                binding.etPassword.transformationMethod = null
                binding.tvSee.setText(R.string.activity_login_email_hide)
                seeClicked = false
            } else {
                // 비밀번호를 숨김으로 설정
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.tvSee.setText(R.string.activity_login_email_see)
                seeClicked = true/*
                binding.etPassword.inputType= InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.tvSee.text = "숨김"
                seeClicked=false
            }else{
                //어떻게 다시 다 가리게 하지??
                binding.etPassword.inputType= InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.tvSee.text = "표시"
                seeClicked=true*/
            }
        }
        questionPass = binding.etPassword
        binding.cvContinue.setOnClickListener {
            if (checkContinue) {
                //TODO: 서버에 보내서 없으면 회원가입 처리?? or 어떻게
                /*    val intent = Intent(this, LoginEmailNextActivity::class.java)
                    startActivity(intent)
                */
            }
        }
        setContentView(binding.root)
    }


    fun isPasswordValid(): Boolean {
        val pattern =
            "^(?=.*[+×÷=/_<>!@#\$%^&*'\":;,?`~\\|{}€£¥₩°•○●□■♤♡◇♧☆▪¤《》¡¿0123456789])[A-Za-z[0-9]+×÷=/_<>!@#\$%^&*'\":;,?`~\\|{}€£¥₩°•○●□■♤♡◇♧☆▪¤《》¡¿]{0,50}$"
        val p = Pattern.matches(pattern, questionPass.text.toString().trim())
        return p
    }

    private fun ButtonOn() {
        binding.etPassword.addTextChangedListener(object : TextWatcher {
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
                if (editable.length >= 8 && isPasswordValid()) {//8자면 이상이여야 하니 ㅇㅇ
                    binding.cvContinue.setBackgroundResource(com.peopleHere.people_here.R.drawable.making_tour_button_next)
                    checkContinue = true
                } else {//클릭 불가능 하게도 설정하기
                    binding.cvContinue.setBackgroundResource(com.peopleHere.people_here.R.drawable.making_tour_button_close)//설정 회색으로
                    checkContinue = false

                }
            }
        })
    }
}