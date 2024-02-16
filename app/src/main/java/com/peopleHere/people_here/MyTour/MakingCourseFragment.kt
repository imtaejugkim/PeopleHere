package com.peopleHere.people_here.MyTour

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peopleHere.people_here.databinding.FragmentMakingCourseBinding

class MakingCourseFragment : Fragment() {

    private lateinit var binding : FragmentMakingCourseBinding
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMakingCourseBinding.inflate(layoutInflater)

        //장소 검색 페이지로 넘어가기 구현
        binding.btnAddPlace.setOnClickListener {
            val intent = Intent(requireContext(), MakingCourseSearchActivity::class.java)
            startActivity(intent)

            //새버전업데이트 말말말말
        }


        return binding.root
    }


}