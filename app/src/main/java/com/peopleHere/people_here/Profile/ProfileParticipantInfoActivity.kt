package com.example.people_here

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.peopleHere.people_here.databinding.ActivityProfileParticipantInfoBinding

class ProfileParticipantActivity() : AppCompatActivity() {
    lateinit var binding : ActivityProfileParticipantInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfileParticipantInfoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}