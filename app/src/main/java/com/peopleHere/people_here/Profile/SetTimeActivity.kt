package com.peopleHere.people_here.Profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.peopleHere.people_here.Local.getJwt
import com.peopleHere.people_here.R
import com.peopleHere.people_here.Remote.AddTourDateView
import com.peopleHere.people_here.Remote.AuthService
import com.peopleHere.people_here.Remote.TourTimeData
import com.peopleHere.people_here.databinding.ActivitySetTimeBinding

class SetTimeActivity : AppCompatActivity() , AddTourDateView{
    lateinit var binding : ActivitySetTimeBinding
    lateinit var date : String
    var month : Int = 0
    var year : Int  = 0
    var tourId : Int = 0
    private var time : TourTimeData?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySetTimeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        date = intent.getStringExtra("date")!!
        month = intent.getIntExtra("month", 0)
        year = intent.getIntExtra("year", 0)
        tourId = intent.getIntExtra("tourId", 0)

        binding.tvMonth.text = month.toString()
        binding.tvDate.text = date

        initNpListener()
        initBack()

        setContentView(binding.root)
    }

    private fun initBack() {
        binding.icCancel.setOnClickListener {
            finish()
        }
    }

    private fun initNpListener() {
        val hourValue = resources.getStringArray(R.array.hour_values)
        val minuteValue = resources.getStringArray(R.array.minute_values)
        val midDayValue = resources.getStringArray(R.array.night_morning_label)

        binding.npHourPicker.minValue = 0
        binding.npHourPicker.maxValue = hourValue.size - 1
        binding.npHourPicker.displayedValues = hourValue

        binding.npMinutePicker.minValue = 0
        binding.npMinutePicker.maxValue = minuteValue.size - 1
        binding.npMinutePicker.displayedValues = minuteValue

        binding.npMorningNight.minValue = 0
        binding.npMorningNight.maxValue = midDayValue.size - 1
        binding.npMorningNight.displayedValues = midDayValue

        binding.btnRegister.setOnClickListener {
            val hourIndex = binding.npHourPicker.value
            val hour = (hourValue[hourIndex]).toInt() + if(binding.npMorningNight.value == 1) 12 else 0
            val minuteIndex = binding.npMinutePicker.value
            val minute = minuteValue[minuteIndex].toInt()

            Log.d("hour",hour.toString())
            Log.d("minute",minute.toString())

            time = TourTimeData(hour, minute, 0, 0)

            val formatDate = "$year-${(month).toString().padStart(2, '0')}-${date.padStart(2, '0')}"

            if(tourId !=0){
                initAddTourManager(tourId, formatDate, time)
            }
        }
    }
    override fun AddTourDateLoading() {
        TODO("Not yet implemented")
    }

    override fun AddTourDateSuccess() {
        val intent = Intent(this, CalendarActivity::class.java)
        intent.putExtra("tourId", tourId)
        startActivity(intent)
        finish()
    }

    override fun AddTourDateFailure(status: Int, message: String) {
        TODO("Not yet implemented")
    }

    private fun initAddTourManager(tourId: Int, date: String, time: TourTimeData?) {
        val token = getJwt()
        Log.d("token",token)
        if(token.isNotEmpty()){
            val authService = AuthService(this)
            authService.setAddTourDateView(this)
            Log.d("tourId",tourId.toString())
            authService.addTourDateInfo(tourId, date, time)
        }else{
            Log.d("token 오류","token 오류")
        }
    }
}