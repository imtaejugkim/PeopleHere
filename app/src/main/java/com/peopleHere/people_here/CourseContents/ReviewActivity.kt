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
    private var userName : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityReviewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        userName = intent.getStringExtra("reviewUser")

        binding.btnBack.setOnClickListener {
            finish()
        }

        reviewData.addAll(
            arrayListOf(
                CourseReviewData("https://cdn.pixabay.com/photo/2017/04/01/21/06/portrait-2194457_1280.jpg", "김태정", "머정", 2024, 2,"정말 완벽한 투어에요!\n한번 더 투어에 참여햘 예정이에요!"),
                CourseReviewData("https://cdn.pixabay.com/photo/2015/07/20/12/57/ambassador-852766_640.jpg", "전우진", "진땅", 2024, 2,"리더님께서 친절해요."),
                CourseReviewData("https://cdn.pixabay.com/photo/2016/11/18/19/07/happy-1836445_1280.jpg", "이동열", "동열", 2024, 2,"이 투어는 최고에요!\n최고!\n최고!\n최고!"),
                CourseReviewData("https://cdn.pixabay.com/photo/2016/11/21/12/42/beard-1845166_1280.jpg", "황정안", "정안", 2024, 2,"리더님이 너무 친절하셨어요. 다음 투어도 참여할예정이에요!"),
                CourseReviewData("https://cdn.pixabay.com/photo/2018/01/17/07/06/laptop-3087585_1280.jpg", "김지현", "지현", 2024, 2,"좋은 추억 쌓고 가요~")
            )
        )

        binding.tvReviewUserName.text = userName
        binding.tvReviewCount.text = 5.toString()

        reviewAdapter = CourseReviewInnerAdapter(applicationContext, reviewData)
        binding.rvReview.adapter = reviewAdapter
        binding.rvReview.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)

        setContentView(binding.root)
    }
}