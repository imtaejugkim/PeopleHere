package com.peopleHere.people_here.CourseContents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.peopleHere.people_here.Data.TourLeaderData
import com.peopleHere.people_here.Local.getJwt
import com.peopleHere.people_here.MainActivity
import com.peopleHere.people_here.Profile.ProfileFragment
import com.peopleHere.people_here.R
import com.peopleHere.people_here.Remote.AuthService
import com.peopleHere.people_here.Remote.RequestEnjoyResponse
import com.peopleHere.people_here.Remote.RequestEnjoyView
import com.peopleHere.people_here.databinding.ActivitySuccessRequestBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SuccessRequestActivity : AppCompatActivity(), RequestEnjoyView{
    lateinit var binding : ActivitySuccessRequestBinding
    private var tourDatesId : Int = 0
    private var requestData : RequestEnjoyResponse? = null
    private var leaderData : TourLeaderData?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySuccessRequestBinding.inflate(layoutInflater)

        tourDatesId = intent.getIntExtra("tourDatesId", 0)
        initDataManager(tourDatesId)

        super.onCreate(savedInstanceState)

        binding.btnSeeMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSeeOther.setOnClickListener {
            val intent = Intent(this, ProfileFragment::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(binding.root)
    }

    private fun initDataManager(tourDatesId: Int) {
        val token = getJwt()
        Log.d("token",token)
        if(token.isNotEmpty()){
            val authService = AuthService(this)
            authService.setRequestEnjoyView(this)
            Log.d("tourDatesId",tourDatesId.toString())
            authService.requestEnjoyInfo(tourDatesId)
        }else{
            Log.d("token 오류","token 오류")
        }
    }

    private fun initRequestEnjoyInfo(
        requestData: RequestEnjoyResponse,
        leaderData: TourLeaderData
    ) {

        val hours = requestData.tourTime / 60
        val minutes = requestData.tourTime % 60
        binding.tvTotalTime.text = if (minutes > 0) {
            "${hours}시간 ${minutes}분"
        } else {
            "${hours}시간"
        }

        val firstPlaceImage = requestData.places.firstOrNull()?.placeImages?.firstOrNull()
        firstPlaceImage?.let { imageUrl ->
            Glide.with(this)
                .load(imageUrl)
                .into(binding.ivTourFirst)
        }

        Glide.with(this)
            .load(leaderData.userImageUrl)
            .into(binding.ivTourLeader)

        if (requestData.places.size > 1) {
            val addCount = requestData.places.size - 1
            binding.tvRegion.text = "${requestData.places[0].placeAddress} 외 ${addCount}개"
        } else if (requestData.places.isNotEmpty()) {
            binding.tvRegion.text = requestData.places[0].placeAddress
        } else {
            binding.tvRegion.text = "위치 정보 없음"
        }

        binding.tvTitle.text = requestData.tourName
        binding.tvWillMeetName.text = leaderData.userName
        binding.tvMeetingPeopleLanguages.text = leaderData.languages.toString()

        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("yyyy년 M월 d일 (E)", Locale.getDefault())
        val date = inputDateFormat.parse(requestData.date) ?: return
        binding.tvDateDay.text = outputDateFormat.format(date)

        if(requestData.time == null){
            binding.llTimeExist.visibility = View.GONE
            binding.tvDateTime.visibility = View.VISIBLE
        }else {
            binding.llTimeExist.visibility = View.VISIBLE
            binding.tvDateTime.visibility = View.GONE

            val inputDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("a hh:mm", Locale.getDefault())
            val startTime = inputDateFormat.parse(requestData.time!!)
            val calendar = Calendar.getInstance().apply {
                time = startTime ?: return
                add(Calendar.MINUTE, requestData.tourTime)
            }
            val endTime = calendar.time

            binding.tvTimeExistFirst.text = outputDateFormat.format(startTime)
            binding.tvTimeExistSecond.text = outputDateFormat.format(endTime)
        }
    }

    override fun RequestEnjoyLoading() {
        TODO("Not yet implemented")
    }

    override fun RequestEnjoySuccess(content: RequestEnjoyResponse) {
        requestData = content
        leaderData = content.tourLeader

        initRequestEnjoyInfo(requestData!!, leaderData!!)
    }

    override fun RequestEnjoyFailure(status: Int, message: String) {
        TODO("Not yet implemented")
    }
}