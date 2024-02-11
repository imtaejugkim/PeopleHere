package com.peopleHere.people_here.CourseContents

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.peopleHere.people_here.Data.CourseQuestionData
import com.peopleHere.people_here.Local.getJwt
import com.peopleHere.people_here.R
import com.peopleHere.people_here.Remote.AuthService
import com.peopleHere.people_here.Remote.CourseContentsResponse
import com.peopleHere.people_here.Remote.CourseContentsView
import com.peopleHere.people_here.databinding.ActivityCourseContentsBinding
import java.lang.Integer.min

class CourseContentsActivity : AppCompatActivity() , CourseContentsView {
    private lateinit var binding: ActivityCourseContentsBinding
    private val imgList = mutableListOf<String>()
    private var questionData : ArrayList<CourseQuestionData> = arrayListOf()
    private var questionAdapter : CoursesQuestionAdapter ?= null
    private lateinit var key : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseContentsBinding.inflate(layoutInflater)
        key = intent.getStringExtra("key") ?: ""

        initViewPager()
        initDataManager(key)

        //백엔드 통신시 추가될 데이터 형식입니다.
        initDummyImageData()
        initDummyQuestionData()

        initRecyclerView()
        initIndicators()
        setupViewPagerListener()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        setContentView(binding.root)
    }

    private fun initDummyQuestionData() {
        questionData.addAll(
            arrayListOf(
                CourseQuestionData("가장 기억에 남는 여행지는?","이탈리아의 로마, Trastevere\n해질 무렵 테베레 강을 건너 광장으로 가면 달빛 아래 매일 밤 끊이지 않는 음악과 거리 공연, 춤추는 사람들..."),
                CourseQuestionData("식도랑 vs 여행지","금강산도 식후경... 여행은 역시 각지의 산해진미를 맛보는 재미 아니겠어요\uD83E\uDD24 하지만 쇼핑도 포기 모태...!"),
                CourseQuestionData("가장 기억에 남는 여행지는?","이탈리아의 로마, Trastevere\n해질 무렵 테베레 강을 건너 광장으로 가면 달빛 아래 매일 밤 끊이지 않는 음악과 거리 공연, 춤추는 사람들...")
            )
        )
    }

    private fun initDummyImageData() {
        imgList.add("https://media.istockphoto.com/id/1482199015/ko/사진/행복한-강아지-웨일스-어-코기-14-주령-개가-윙크하고-헐떡이고-흰색에-고립되어-앉아-있습니다.jpg?s=612x612&w=is&k=20&c=CkTkWxs_QitkIcwMhbE155bnuLBoRBQ_AQaDNRh0Bh8=")
        imgList.add("https://cdn.pixabay.com/photo/2019/08/07/14/11/dog-4390885_1280.jpg")
        imgList.add("https://cdn.pixabay.com/photo/2019/07/23/13/51/shepherd-dog-4357790_1280.jpg")
        imgList.add("https://cdn.pixabay.com/photo/2016/12/13/05/15/puppy-1903313_1280.jpg")
        imgList.add("https://cdn.pixabay.com/photo/2017/09/25/13/12/puppy-2785074_1280.jpg")
        imgList.add("https://media.istockphoto.com/id/1480747819/ko/사진/닥스-순드와-고양이-가장-친한-친구.jpg?s=2048x2048&w=is&k=20&c=lyjV_IffYM2g2xAey6T6uon4gYkbu_KRlKnZsWPg_ZU=")
        imgList.add("https://cdn.pixabay.com/photo/2018/05/11/08/11/dog-3389729_1280.jpg")
        imgList.add("https://media.istockphoto.com/id/1267541412/ko/사진/빨간-산타-클로스-모자와-웃는-표정으로-크리스마스를-축하하는-행복한-강아지-개.jpg?s=2048x2048&w=is&k=20&c=iDp7DehrScAiZTNH-MnP05eOOaTpN3SVv-8DIH5dfXY=")
    }

    private fun initRecyclerView() {
        questionAdapter = CoursesQuestionAdapter(questionData)
        binding.rvTourContentsQuestion.adapter = questionAdapter
        binding.rvTourContentsQuestion.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
    }


    private fun initViewPager() {
        binding.vpTourContents.adapter = CourseContentsImageAdapter(applicationContext, imgList)
        binding.vpTourContents.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun initIndicators() {
        val indicators = Array(min(imgList.size, 5)) { ImageView(this) }
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(6, 0, 6, 0)
        }

        for (indicator in indicators) {
            indicator.apply {
                setImageResource(R.drawable.ic_indicator_inactive)
                setLayoutParams(layoutParams)
            }
            binding.llIndicatorContainer.addView(indicator)
        }

        if (binding.llIndicatorContainer.childCount > 0) {
            (binding.llIndicatorContainer.getChildAt(0) as ImageView).setImageResource(R.drawable.ic_indicator_active) // 첫 번째 원 활성화
        }
    }

    // ViewPager 변경 리스너를 설정하는 함수
    private fun setupViewPagerListener() {
        binding.vpTourContents.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateIndicators(position)
            }
        })
    }

    // 현재 페이지에 따라 인디케이터를 업데이트하는 함수
    private fun updateIndicators(position: Int) {
        val childCount = binding.llIndicatorContainer.childCount
        val indicatorPosition = when {
            imgList.size <= 5 -> position
            position >= imgList.size - 1 -> 4 // 마지막 이미지
            position >= 3 -> 3 // 네 번째 이미지부터 마지막에서 두 번째 이미지까지
            else -> position // 그 이외의 경우
        }

        for (i in 0 until childCount) {
            val imageView = binding.llIndicatorContainer.getChildAt(i) as ImageView
            if (i == indicatorPosition) {
                imageView.setImageResource(R.drawable.ic_indicator_active)
            } else {
                imageView.setImageResource(R.drawable.ic_indicator_inactive)
            }
        }
    }

    private fun initDataManager(tourId : String) {
        val token = getJwt()
        Log.d("token",token)
        if(token.isNotEmpty()){
            val authService = AuthService()
            authService.setCourseContentsView(this)
            authService.courseContentsInfo(tourId)
        }else{
            Log.d("token 오류","token 오류")
        }
    }

    override fun CourseContentsLoading() {
        TODO("Not yet implemented")
    }

    override fun CourseContentsSuccess(content: CourseContentsResponse) {
        TODO("Not yet implemented")
    }

    override fun CourseContentsFailure(status: Int, message: String) {
        TODO("Not yet implemented")
    }
}