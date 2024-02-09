package com.example.people_here.SignUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}