package com.example.people_here.MakingTour

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.people_here.Data.OnBoardingData
import com.example.people_here.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity(){

    private lateinit var binding :ActivityOnBoardingBinding
    private var onBoardingAdapter: OnBoardingAdapter ?= null
    private var onBoardingData : ArrayList<OnBoardingData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initOnBoardingData()

        binding.btnNext.setOnClickListener {
            val intent = Intent()

        }
    }

    private fun initOnBoardingData() {
        TODO("Not yet implemented")
    }

    private fun initRecyclerView() {
        onBoardingAdapter = OnBoardingAdapter(onBoardingData)
        binding.rvOnBoarding.adapter = onBoardingAdapter
        binding.rvOnBoarding.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
    }

}