package com.example.people_here.TourContents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.people_here.databinding.ActivityTourContentsBinding

class TourContentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTourContentsBinding
    private val imgList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTourContentsBinding.inflate(layoutInflater)
        intent.getSerializableExtra("key")

        initViewPager()

        //백엔드 통신시 추가될 데이터 형식입니다.
        initDummyData()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        setContentView(binding.root)
    }

    private fun initDummyData() {
        imgList.add("https://media.istockphoto.com/id/1482199015/ko/사진/행복한-강아지-웨일스-어-코기-14-주령-개가-윙크하고-헐떡이고-흰색에-고립되어-앉아-있습니다.jpg?s=612x612&w=is&k=20&c=CkTkWxs_QitkIcwMhbE155bnuLBoRBQ_AQaDNRh0Bh8=")
        imgList.add("https://cdn.pixabay.com/photo/2019/08/07/14/11/dog-4390885_1280.jpg")
        imgList.add("https://cdn.pixabay.com/photo/2019/07/23/13/51/shepherd-dog-4357790_1280.jpg")
        imgList.add("https://cdn.pixabay.com/photo/2016/12/13/05/15/puppy-1903313_1280.jpg")
        imgList.add("https://cdn.pixabay.com/photo/2017/09/25/13/12/puppy-2785074_1280.jpg")
        imgList.add("https://media.istockphoto.com/id/1480747819/ko/사진/닥스-순드와-고양이-가장-친한-친구.jpg?s=2048x2048&w=is&k=20&c=lyjV_IffYM2g2xAey6T6uon4gYkbu_KRlKnZsWPg_ZU=")
        imgList.add("https://cdn.pixabay.com/photo/2018/05/11/08/11/dog-3389729_1280.jpg")
        imgList.add("https://media.istockphoto.com/id/1267541412/ko/사진/빨간-산타-클로스-모자와-웃는-표정으로-크리스마스를-축하하는-행복한-강아지-개.jpg?s=2048x2048&w=is&k=20&c=iDp7DehrScAiZTNH-MnP05eOOaTpN3SVv-8DIH5dfXY=")
    }

    private fun initViewPager() {
        binding.vpTourContents.adapter = TourContentsImageAdapter(applicationContext, imgList)
        binding.vpTourContents.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }
}