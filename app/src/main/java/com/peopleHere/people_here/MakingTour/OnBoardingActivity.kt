package com.peopleHere.people_here.MakingTour

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.peopleHere.people_here.CourseContents.CourseContentsImageAdapter
import com.peopleHere.people_here.Data.OnBoardingData
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity(){

    private lateinit var binding :ActivityOnBoardingBinding
    private var onBoardingAdapter: OnBoardingAdapter ?= null
    private var onBoardingData : ArrayList<OnBoardingData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initOnBoardingData()
        initViewPager()
        initIndicators()
        setupViewPagerListener()

        binding.btnNext.setOnClickListener {
            val intent = Intent()

        }
    }

    private fun initOnBoardingData() {
        onBoardingData.addAll(
            arrayListOf(
                OnBoardingData(R.drawable.ic_onboarding_1, 0, "피플히어의 여행자들과 만나보세요"),
                OnBoardingData(R.drawable.ic_onboarding_2, 1, "내 취향의 장소들로 코스를 계획해요"),
                OnBoardingData(R.drawable.ic_onboarding_3, 2, "코스를 따라 무엇을 하는지 소개해요"),
                OnBoardingData(R.drawable.ic_onboarding_4, 3, "참여 가능한 날짜를 정하면 준비 완료!"),
                OnBoardingData(R.drawable.ic_onboarding_4, 4, "여행자와 만나 즐거운 시간을 보내세요")
            )
        )
    }
    private fun initViewPager() {
        binding.vpOnBoarding.adapter = OnBoardingAdapter(onBoardingData)
        binding.vpOnBoarding.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun initIndicators() {
        val indicators = Array(Integer.min(onBoardingData.size, 5)) { ImageView(this) }
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(6, 0, 6, 0)
        }

        for (indicator in indicators) {
            indicator.apply {
                setImageResource(R.drawable.ic_on_boarding_active)
                setLayoutParams(layoutParams)
            }
            binding.llIndicatorContainer.addView(indicator)
        }

        if (binding.llIndicatorContainer.childCount > 0) {
            (binding.llIndicatorContainer.getChildAt(0) as ImageView).setImageResource(R.drawable.ic_on_boarding_active) // 첫 번째 원 활성화
        }
    }

    // ViewPager 변경 리스너를 설정하는 함수
    private fun setupViewPagerListener() {
        binding.vpOnBoarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateIndicators(position)
            }
        })
    }

    // 현재 페이지에 따라 인디케이터를 업데이트하는 함수
    private fun updateIndicators(position: Int) {
        val childCount = binding.llIndicatorContainer.childCount
        val indicatorPosition = when {
            onBoardingData.size <= 5 -> position
            position >= onBoardingData.size - 1 -> 4 // 마지막 이미지
            position >= 3 -> 3 // 네 번째 이미지부터 마지막에서 두 번째 이미지까지
            else -> position // 그 이외의 경우
        }

        for (i in 0 until childCount) {
            val imageView = binding.llIndicatorContainer.getChildAt(i) as ImageView
            if (i == indicatorPosition) {
                imageView.setImageResource(R.drawable.ic_on_boarding_active)
            } else {
                imageView.setImageResource(R.drawable.ic_indicator_inactive)
            }
        }
    }

}