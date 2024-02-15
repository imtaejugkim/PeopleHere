package com.peopleHere.people_here.CourseContents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivityPossibleEnjoyBinding
import com.peopleHere.people_here.databinding.ActivityRequestEnjoyBinding

class RequestEnjoyActivity : AppCompatActivity() {
    lateinit var binding : ActivityRequestEnjoyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRequestEnjoyBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}