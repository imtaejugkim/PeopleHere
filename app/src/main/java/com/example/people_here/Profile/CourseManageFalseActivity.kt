package com.example.people_here.Profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.people_here.MakingTour.MakingTourAddListActivity
import com.example.people_here.databinding.ActivityCourseManageFalseBinding

class CourseManageFalseActivity : AppCompatActivity() {
    lateinit var binding : ActivityCourseManageFalseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCourseManageFalseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnMakingCourse.setOnClickListener {
            val intent = Intent(this, MakingTourAddListActivity::class.java)
            startActivity(intent)
        }
    }
}