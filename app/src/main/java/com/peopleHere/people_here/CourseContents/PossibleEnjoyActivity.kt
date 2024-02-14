package com.peopleHere.people_here.CourseContents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.peopleHere.people_here.Data.MainData
import com.peopleHere.people_here.Local.getJwt
import com.peopleHere.people_here.Main.MainAdapter
import com.peopleHere.people_here.R
import com.peopleHere.people_here.Remote.AuthService
import com.peopleHere.people_here.Remote.UpcomingDateResponse
import com.peopleHere.people_here.Remote.UpcomingDateView
import com.peopleHere.people_here.databinding.ActivityPossibleEnjoyBinding
import java.text.SimpleDateFormat
import java.util.Locale

class PossibleEnjoyActivity : AppCompatActivity() , UpcomingDateView {
    lateinit var binding : ActivityPossibleEnjoyBinding
    private var upcomingData : ArrayList<UpcomingDateResponse> = arrayListOf()
    private var key : Int = 0
    private var tourTime : Int = 0
    private var classifiedData: Map<String, ArrayList<UpcomingDateResponse>> = mapOf()
    private var enjoyAdapter : PossibleEnjoyAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPossibleEnjoyBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        key = intent.getIntExtra("key", 0)
        tourTime = intent.getIntExtra("tourTime", 0)
        initDataManager(key)

        initRecyclerView()

        binding.btnBack.setOnClickListener {
            finish()
        }

        setContentView(binding.root)
    }

    override fun UpcomingDateLoading() {
        TODO("Not yet implemented")
    }

    override fun UpcomingDateSuccess(content: ArrayList<UpcomingDateResponse>) {
        upcomingData = content
        classifiedData = classifyDataByMonth(content)

        binding.tvReviewCount.text = upcomingData.size.toString()
        enjoyAdapter?.notifyDataSetChanged()

        initRecyclerView()
    }

    override fun UpcomingDateFailure(status: Int, message: String) {
        Log.d("Possible Enjoy Activity 통신 오류1", status.toString())
        Log.d("Possible Enjoy Activity 통신 오류2", message)
    }

    private fun classifyDataByMonth(data: List<UpcomingDateResponse>): Map<String, ArrayList<UpcomingDateResponse>> {
        val dateFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        return data.groupBy { upcomingDateResponse ->
            val date = dateFormat.parse(upcomingDateResponse.date) ?: return@groupBy ""
            dateFormat.format(date)
        }.mapValues { entry -> ArrayList(entry.value) }
    }

    private fun initRecyclerView() {
        val enjoyAdapter = PossibleEnjoyAdapter(classifiedData, tourTime, object : PossibleEnjoyInnerAdapter.OnItemClickListener {
            override fun onItemClick(dateInfo: UpcomingDateResponse) {
                // 항목 클릭 처리
            }
        })

        binding.rvComingDate.adapter = enjoyAdapter
        binding.rvComingDate.layoutManager = LinearLayoutManager(this
            , LinearLayoutManager.VERTICAL, false)
    }

    private fun initDataManager(tourId : Int) {
        val token = getJwt()
        Log.d("token",token)
        if(token.isNotEmpty()){
            val authService = AuthService()
            authService.setUpcomingDateView(this)
            Log.d("tourId",tourId.toString())
            authService.upcomingDateInfo(tourId)
        }else{
            Log.d("token 오류","token 오류")
        }
    }
}