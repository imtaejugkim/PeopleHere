package com.example.people_here.MakingTour

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.people_here.databinding.ActivityMakingTourTimeBinding

class MakingTourTimeActivity : AppCompatActivity() {
    lateinit var binding : ActivityMakingTourTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakingTourTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}