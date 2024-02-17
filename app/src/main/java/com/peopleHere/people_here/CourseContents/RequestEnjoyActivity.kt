package com.peopleHere.people_here.CourseContents

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.peopleHere.people_here.databinding.ActivityRequestEnjoyBinding

class RequestEnjoyActivity : AppCompatActivity() {
    lateinit var binding : ActivityRequestEnjoyBinding
    private var dates : String ?= null
    var tourDatesId : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRequestEnjoyBinding.inflate(layoutInflater)

        binding.tvService.text = Html.fromHtml("<u>피플히어의 서비스 정책<u>")

        dates = intent.getStringExtra("dates")
        tourDatesId = intent.getIntExtra("tourDatesId", 0)
        Log.d("dates",dates.toString())
        Log.d("tourDatesId",tourDatesId.toString())

        binding.tvService.setOnClickListener {
            val intent = Intent(this, AppServiceActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}