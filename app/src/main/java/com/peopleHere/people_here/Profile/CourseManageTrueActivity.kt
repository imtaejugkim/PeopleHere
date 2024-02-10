package com.peopleHere.people_here.Profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.peopleHere.people_here.databinding.ActivityCourseManageTrueBinding

class CourseManageTrueActivity : AppCompatActivity() {
    lateinit var binding : ActivityCourseManageTrueBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCourseManageTrueBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}