package com.peopleHere.people_here.Profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivitySetTimeBinding

class SetTimeActivity : AppCompatActivity(){
    lateinit var binding : ActivitySetTimeBinding
    var date : String = ""
    var month : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySetTimeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        date = intent.getStringExtra("date")!!
        month = intent.getIntExtra("month", 0)

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
            val hour = hourValue[hourIndex]
            val minuteIndex = binding.npMinutePicker.value
            val minute = minuteValue[minuteIndex]


        }
    }
}