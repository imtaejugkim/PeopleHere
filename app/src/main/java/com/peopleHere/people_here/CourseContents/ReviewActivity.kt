package com.peopleHere.people_here.CourseContents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.peopleHere.people_here.Data.CourseReviewData
import com.peopleHere.people_here.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity() {
    lateinit var binding : ActivityReviewBinding
    private var reviewData : ArrayList<CourseReviewData> = arrayListOf()
    private var reviewAdapter : CourseReviewInnerAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityReviewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        binding.btnBack.setOnClickListener {
            finish()
        }

        reviewData.addAll(
            arrayListOf(
                CourseReviewData("https://cdn.pixabay.com/photo/2015/11/26/00/14/woman-1063100_1280.jpg", "더미맨", "더미더미", 2023, 1,"사장님이 최고에요!"),
                CourseReviewData("https://cdn.pixabay.com/photo/2015/11/26/00/14/woman-1063100_1280.jpg", "더미맨", "더미더미", 2023, 2,"사장님이 나빠요!"),
                CourseReviewData("https://cdn.pixabay.com/photo/2015/11/26/00/14/woman-1063100_1280.jpg", "더미맨", "더미더미", 2023, 3,"사장님이 최고에요!\n최고\n최고\n최고"),
                CourseReviewData("https://cdn.pixabay.com/photo/2015/11/26/00/14/woman-1063100_1280.jpg", "더미맨", "더미더미", 2023, 4,"사장님이 최고에요!"),
                CourseReviewData("https://cdn.pixabay.com/photo/2015/11/26/00/14/woman-1063100_1280.jpg", "더미맨", "더미더미", 2023, 5,"사장님이 나빠요!")
            )
        )

        reviewAdapter = CourseReviewInnerAdapter(applicationContext, reviewData)
        binding.rvReview.adapter = reviewAdapter
        binding.rvReview.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)

        setContentView(binding.root)
    }
}