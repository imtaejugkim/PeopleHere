package com.peopleHere.people_here.SignUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.peopleHere.people_here.AddPicture.LocationChooseFragment
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        binding.cvBirth.setOnClickListener {
            val bottomsheet = BirthFragment()
            bottomsheet.show(supportFragmentManager, bottomsheet.tag)
        }
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        Log.d("tjf","start")

    }
    override fun onResume() {
        super.onResume()
        //왜 두번은 실행 안하지
        //결론은 이게 안 돌아서니까 shraed로 해야하나 흠..?

        val receivedIntent = intent//fragment에서 돌아온 경우
        //왜 못 받는건가 자네?
        var year = receivedIntent.getStringExtra("year")
        var month = receivedIntent.getStringExtra("month")
        var day = receivedIntent.getStringExtra("day")

        Log.d("tjf", year.toString())
        //TODO:왜 null로 나올까?

        var userBirth = "$year/$month/$day"
        binding.tvBirth.text = userBirth
        val colorGray8 = ContextCompat.getColor(this, R.color.Gray8)
        binding.tvBirth.setTextColor(colorGray8)

    }
}