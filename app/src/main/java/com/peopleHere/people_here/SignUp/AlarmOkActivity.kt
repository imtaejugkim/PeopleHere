package com.peopleHere.people_here.SignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.peopleHere.people_here.MainActivity
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivityAlarmOkBinding
import com.peopleHere.people_here.databinding.FragmentBirthBinding

class AlarmOkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmOkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmOkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnYes.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ok","alarm")
            //TODO:서버에다가 회원가입 정보 다 넣기
            startActivity(intent)
        }
        binding.tvLater.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            //TODO:서버에다가 회원가입 정보 다 넣기
            startActivity(intent)

        }
    }


}