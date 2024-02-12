package com.peopleHere.people_here.CourseContents

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.peopleHere.people_here.Data.CourseQuestionData
import com.peopleHere.people_here.Data.CourseReviewData
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
    private var reviewData : ArrayList<CourseReviewData> = arrayListOf()
    private var reviewAdapter : CourseReviewAdapter ?= null
    private var key : Int = 0
    private var courseData : CourseContentsResponse ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseContentsBinding.inflate(layoutInflater)
        key = intent.getIntExtra("key", 0)

        initViewPager()
        initDataManager(key)

        //백엔드 통신시 추가될 데이터 형식입니다.
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

        reviewData.addAll(
            arrayListOf(
                CourseReviewData("https://cdn.pixabay.com/photo/2015/11/26/00/14/woman-1063100_1280.jpg", "더미맨", "더미더미", 2023, 1,"사장님이 최고에요!"),
                CourseReviewData("https://cdn.pixabay.com/photo/2015/11/26/00/14/woman-1063100_1280.jpg", "더미맨", "더미더미", 2023, 2,"사장님이 나빠요!"),
                CourseReviewData("https://cdn.pixabay.com/photo/2015/11/26/00/14/woman-1063100_1280.jpg", "더미맨", "더미더미", 2023, 3,"사장님이 최고에요!\n최고\n최고\n최고"),
                CourseReviewData("https://cdn.pixabay.com/photo/2015/11/26/00/14/woman-1063100_1280.jpg", "더미맨", "더미더미", 2023, 4,"사장님이 최고에요!"),
                CourseReviewData("https://cdn.pixabay.com/photo/2015/11/26/00/14/woman-1063100_1280.jpg", "더미맨", "더미더미", 2023, 5,"사장님이 나빠요!")
            )
        )
    }

    private fun initRecyclerView() {
        questionAdapter = CoursesQuestionAdapter(questionData)
        binding.rvTourContentsQuestion.adapter = questionAdapter
        binding.rvTourContentsQuestion.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)

        reviewAdapter = CourseReviewAdapter(applicationContext, reviewData)
        binding.rvReview.adapter = reviewAdapter
        binding.rvReview.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        val itemDecorationMargin = resources.getDimensionPixelSize(R.dimen.recycler_view_item_margin)
        binding.rvReview.addItemDecoration(MarginRecyclerItem(itemDecorationMargin))

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvReview)
    }

    private fun initViewPager() {
        binding.vpTourContents.adapter = CourseContentsImageAdapter(applicationContext, imgList)
        binding.vpTourContents.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun initIndicators() {
        binding.llIndicatorContainer.removeAllViews()
        val indicatorsCount = min(imgList.size, 5) // 인디케이터의 개수는 이미지 리스트의 크기와 5 중 작은 값
        val indicators = Array(indicatorsCount) { ImageView(this) }
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
        val indicatorsCount = binding.llIndicatorContainer.childCount
        val actualPosition = when {
            imgList.size <= 5 -> position // 5개 이하인 경우, position
            position >= imgList.size - 1 -> indicatorsCount - 1 // 마지막 이미지인 경우, 인디케이터
            position >= 4 && imgList.size > 5 -> min(position, indicatorsCount - 1) // 이미지 개수가 5개 초과인 경우, 4번째부터는 위치 고정
            else -> position
        }

        for (i in 0 until indicatorsCount) {
            val imageView = binding.llIndicatorContainer.getChildAt(i) as ImageView
            if (i == actualPosition) {
                imageView.setImageResource(R.drawable.ic_indicator_active)
            } else {
                imageView.setImageResource(R.drawable.ic_indicator_inactive)
            }
        }
    }

    private fun initDataManager(tourId : Int) {
        val token = getJwt()
        Log.d("token",token)
        if(token.isNotEmpty()){
            val authService = AuthService()
            authService.setCourseContentsView(this)
            Log.d("tourId",tourId.toString())
            authService.courseContentsInfo(tourId)
        }else{
            Log.d("token 오류","token 오류")
        }
    }

    override fun CourseContentsLoading() {
        TODO("Not yet implemented")
    }

    override fun CourseContentsSuccess(content: CourseContentsResponse) {
        courseData = content

        initCourseInfo(courseData!!)
    }

    private fun initCourseInfo(courseData : CourseContentsResponse) {
        // contents 젤 위 사진
        val newImgList = courseData.places.mapNotNull {
            it.imageUrls.firstOrNull() }
        (binding.vpTourContents.adapter as CourseContentsImageAdapter).updateImages(newImgList)

        initIndicators()
        binding.vpTourContents.currentItem = binding.vpTourContents.currentItem

        // 코스 정보
        binding.tvTourTitle.text = courseData.tourName
        binding.tvTourRegion.text = courseData.places.map {
            it.address[0]
        }.toString()
        binding.tvTourTime.text = courseData.time

        // 코스 대장
        binding.tvMeetingPeopleName.text = courseData.userName
        val userImageUrl = courseData.userImageUrl
        if (userImageUrl.startsWith("https://")) {
            Glide.with(this)
                .load(userImageUrl)
                .into(binding.ivMeetingPeopleImage)
        }

        // 코스 사진 설명
        initDayTripInfo(newImgList)
        binding.tvMeetingCourseInfo.post {
            binding.tvMeetingCourseInfo.text = courseData.content
            if (binding.tvMeetingCourseInfo.lineCount > 10) {
                binding.btnCourseInfoMore.visibility = View.VISIBLE
            }else{
                binding.btnCourseInfoMore.visibility = View.INVISIBLE
            }
        }
        binding.btnCourseInfoMore.setOnClickListener {
            binding.tvMeetingCourseInfo.maxLines = Integer.MAX_VALUE
        }

        // 코스 후기 넘어가기
        initReview(courseData)
    }

    private fun initReview(courseData: CourseContentsResponse) {
        binding.llReviewButton.setOnClickListener {
            val intent = Intent(this, ReviewActivity()::class.java)
            intent.putExtra("key",reviewData)
            startActivity(intent)
        }
    }

    private fun initDayTripInfo(imgUrls: List<String>) {
        when(imgUrls.size) {
            0,1 -> {
                binding.viewCourseImages1.visibility = View.VISIBLE
                binding.viewCourseImages2.visibility = View.GONE
                binding.viewCourseImages3.visibility = View.GONE
                binding.tvAddImage.visibility = View.GONE
                Glide.with(this).load(imgUrls[0]).into(binding.ivDayTripInfo1)
            }
            2 -> {
                binding.viewCourseImages1.visibility = View.GONE
                binding.viewCourseImages2.visibility = View.VISIBLE
                binding.viewCourseImages3.visibility = View.GONE
                binding.tvAddImage.visibility = View.GONE
                Glide.with(this).load(imgUrls[0]).into(binding.ivDayTripInfo2)
                Glide.with(this).load(imgUrls[1]).into(binding.ivDayTripInfo3)
            }

            3 -> {
                binding.viewCourseImages1.visibility = View.GONE
                binding.viewCourseImages2.visibility = View.GONE
                binding.viewCourseImages3.visibility = View.VISIBLE
                binding.tvAddImage.visibility = View.GONE
                Glide.with(this).load(imgUrls[0]).into(binding.ivDayTripInfo4)
                Glide.with(this).load(imgUrls[1]).into(binding.ivDayTripInfo5)
                Glide.with(this).load(imgUrls[2]).into(binding.ivDayTripInfo6)
            }
            else ->{
                binding.viewCourseImages1.visibility = View.GONE
                binding.viewCourseImages2.visibility = View.GONE
                binding.viewCourseImages3.visibility = View.VISIBLE
                binding.tvAddImage.visibility = View.VISIBLE
                Glide.with(this).load(imgUrls[0]).into(binding.ivDayTripInfo4)
                Glide.with(this).load(imgUrls[1]).into(binding.ivDayTripInfo5)
                Glide.with(this).load(imgUrls[2]).into(binding.ivDayTripInfo6)
                binding.tvAddImage.text = "+${imgUrls.size - 3}"
            }
        }

        binding.viewEntireImages.setOnClickListener {
            val intent = Intent(this, FullImageActivity()::class.java)
            intent.putExtra("imgSize",imgUrls.size)
            intent.putStringArrayListExtra("imgList", ArrayList(imgUrls))
            startActivity(intent)
        }
    }

    override fun CourseContentsFailure(status: Int, message: String) {
        Log.d("통신O에러1",status.toString())
        Log.d("통신O에러2",message)
    }
}