package com.peopleHere.people_here.Profile

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.datepicker.DayViewDecorator
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivityCourseDateBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class CourseDateActivity : AppCompatActivity() {
    lateinit var binding : ActivityCourseDateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDateBinding.inflate(layoutInflater)

        //1. calendar 날짜 따오기
        /*binding.calendar.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            Log.d("qwert", calendarView.toString() +  " " + year.toString() + " " + month.toString() + " " + dayOfMonth.toString())
        }*/

        binding.materialCalendar.addDecorator(DayDecorator(applicationContext))
        binding.materialCalendar.addDecorator(TodayDecorator())

        setContentView(binding.root)
    }
}

class DayDecorator(context : Context) : DayViewDecorator {
    val drawable = ContextCompat.getDrawable(context, R.drawable.selector_bnv_calendar)
    override fun shouldDecorate(day: CalendarDay?): Boolean {
/*
        return day?.day == 1 // 1이 될 때만 꾸미겠다 의미
*/
        return true // 전체
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(drawable!!)
    }

}

class TodayDecorator : DayViewDecorator {
    val calendar = CalendarDay.today()
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        /*
                return day?.day == 1 // 1이 될 때만 꾸미겠다 의미
        */
        return calendar == day // 오늘 날짜
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object : ForegroundColorSpan(Color.BLUE){

        })
    }

}