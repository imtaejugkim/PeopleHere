package com.peopleHere.people_here.CourseContents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivityFullImageBinding

class FullImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullImageBinding
    private lateinit var imgList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imgList = intent.getStringArrayListExtra("imgList") ?: arrayListOf()

        val adapter = FullImageAdapter(this, imgList)
        binding.fullscreenViewPager.adapter = adapter

        val startPosition = intent.getIntExtra("position", 0)
        binding.fullscreenViewPager.setCurrentItem(startPosition, false)

        updatePositionDisplay(startPosition + 1, imgList.size)

        binding.fullscreenViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 위치 표시 업데이트
                updatePositionDisplay(position + 1, imgList.size)
            }
        })

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun updatePositionDisplay(current: Int, total: Int) {
        binding.tvCurrent.text = current.toString()
        binding.tvEntire.text = total.toString()
    }
}