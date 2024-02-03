package com.example.people_here.MyTour

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.people_here.MakingTour.MakingTourAddListActivity
import com.example.people_here.MakingTour.OnBoardingActivity
import com.example.people_here.databinding.FragmentMakingCourseBinding

class MakingCourseFragment : Fragment() {

    private lateinit var binding : FragmentMakingCourseBinding
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMakingCourseBinding.inflate(layoutInflater)

        //장소 검색 페이지로 넘어가기 구현
        binding.btnAddPlace.setOnClickListener {
            val intent = Intent(requireContext(), OnBoardingActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }


}