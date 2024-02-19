package com.peopleHere.people_here.MakingTour

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.peopleHere.people_here.AddPicture.AddPictureActivity
import com.peopleHere.people_here.Data.MainCourseData
import com.peopleHere.people_here.Data.MainData
import com.peopleHere.people_here.Main.MainAdapter
import com.peopleHere.people_here.databinding.ActivityMakingCourseFinishBinding
import com.peopleHere.people_here.databinding.ActivityMakingTourTimeBinding

class MakingTourCourseFinishActivity : AppCompatActivity() {
    lateinit var binding : ActivityMakingCourseFinishBinding
    private var finishPreviewAdapter : FinishPreviewAdapter ?= null
    private var finishData : ArrayList<MainCourseData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakingCourseFinishBinding.inflate(layoutInflater)

        initImageRecyclerView()
        initCategoryRecyclerView()
        initPreview()

        binding.clPreview.setOnClickListener {
            val intent = Intent(this, PreviewActivity::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)
    }

    private fun initPreview() {
        binding.tvMainTourListTitle.text = "투어 타이틀 이름 여기"
        binding.tvMainTourListLocation.text = "지역 여기"
        binding.tvMainTourListTime.text = "시간 여기"
    }

    private fun initCategoryRecyclerView() {
        Log.d("카테고리 어뎁터", "카테고리 어뎁터해야함")
    }

    private fun initImageRecyclerView() {
        finishPreviewAdapter = FinishPreviewAdapter(finishData)
        binding.rvPreview.adapter = finishPreviewAdapter
        binding.rvPreview.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
    }
}