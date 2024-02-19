package com.peopleHere.people_here.MyTour

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.peopleHere.people_here.databinding.ActivityMakingCourseBinding

class MakingCourseActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMakingCourseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMakingCourseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)


        binding.btnAddPlace.setOnClickListener {
            val intent = Intent(this, MakingCourseSearchActivity::class.java)
            startActivity(intent)

        }

        setContentView(binding.root)
    }



}