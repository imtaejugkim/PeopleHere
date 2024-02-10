package com.example.people_here.SignUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.people_here.AddPicture.LocationChooseFragment
import com.example.people_here.R
import com.example.people_here.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        binding.cvBirth.setOnClickListener {
            val bottomsheet = BirthFragment()
            bottomsheet.show(supportFragmentManager, bottomsheet.tag)
        }
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val receivedIntent=intent//fragment에서 돌아온 경우
        var year=receivedIntent.getStringExtra("year")
        var month=receivedIntent.getStringExtra("month")
        var day=receivedIntent.getStringExtra("day")
        Log.d("tjf",year.toString())
        var userBirth= "$year/$month/$day"
        binding.tvBirth.text=userBirth
        val colorGray8 = ContextCompat.getColor(this, R.color.Gray8)
        binding.tvBirth.setTextColor(colorGray8)

    }
}