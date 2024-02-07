package com.example.people_here.Profile

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.people_here.databinding.ActivityCalendarBinding
import java.util.Calendar

class CalendarActivity : AppCompatActivity() {
    lateinit var binding : ActivityCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvWeek.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvDate.layoutManager = GridLayoutManager(this,7)

        binding.rvWeek.adapter = WeekAdapter(arrayListOf("M","T","W","T","F","S","S"))

        var dayList = setDayList()
        binding.rvDate.adapter = DateAdapter(dayList)

    }

    private fun setDayList() : ArrayList<String> {
        var cal = Calendar.getInstance()
        cal.set(Calendar.DATE, 1)
        var startWeekday = cal.get(Calendar.DAY_OF_WEEK) // 토요일이면 8
        var lastDay = cal.getActualMaximum(Calendar.DATE)

        // 전 년도로 반영
        if (cal.get(Calendar.MONTH) == 0) {
            cal.set(cal.get(Calendar.YEAR) - 1, 11, 1)
        } else {
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 1, 1)
        }

        var previousMonthLastDay = cal.getActualMaximum(Calendar.DATE)
        Log.d("qwety",previousMonthLastDay.toString())

        var dayList = ArrayList<String>()

        for (i in startWeekday - 2 downTo 0) {
            dayList.add((previousMonthLastDay - i).toString())
        }
        for (i in 1..lastDay) {
            dayList.add(i.toString())
        }

        var dayCount = 1
        while (dayList.size < 42) {
            dayList.add(dayCount.toString())
            dayCount++
        }

        return dayList
    }


}