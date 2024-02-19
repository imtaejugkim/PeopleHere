package com.peopleHere.people_here.MakingTour

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.peopleHere.people_here.AddPicture.AddPictureActivity
import com.peopleHere.people_here.ApplicationClass
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivityMakingTourTimeBinding

class MakingTourTimeActivity : AppCompatActivity() {
    lateinit var binding : ActivityMakingTourTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakingTourTimeBinding.inflate(layoutInflater)

        binding.btnMakingTourTimeNext.setOnClickListener {
            val intent = Intent(this, AddPictureActivity::class.java)
            startActivity(intent)
        }

        initBack()
        initNpListener()


        setContentView(binding.root)
    }

    private fun initBack() {
        binding.btnMakingTourBefore.setOnClickListener {
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

        binding.btnMakingTourTimeNext.setOnClickListener {
            val hourIndex = binding.npHourPicker.value
            val hour = hourValue[hourIndex].toInt()
            val minuteIndex = binding.npMinutePicker.value
            val minute = minuteValue[minuteIndex].toInt()


            val intent = Intent(this, AddPictureActivity::class.java)
            intent.putExtra("time",hour * 60 + minute)
            ApplicationClass.ptourTime=hour * 60 + minute
            Log.d("APP_ptour",ApplicationClass.ptourTime.toString())

            startActivity(intent)
        }
    }
}