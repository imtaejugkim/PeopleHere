package com.peopleHere.people_here.CourseContents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.peopleHere.people_here.Data.CourseReviewData
import com.peopleHere.people_here.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity() {
    lateinit var binding : ActivityReviewBinding
    val key : ArrayList<CourseReviewData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityReviewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)



        setContentView(binding.root)
    }
}