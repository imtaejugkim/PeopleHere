package com.peopleHere.people_here.MyTour

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peopleHere.people_here.MakingTour.MakingTourAddListActivity
import com.peopleHere.people_here.databinding.FragmentMakingCourseBinding
import com.peopleHere.people_here.databinding.FragmentMakingCourseCheckBinding

class MakingCourseCheckFragment : Fragment(){
    private lateinit var binding : FragmentMakingCourseCheckBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMakingCourseCheckBinding.inflate(layoutInflater)

        //다시 선택 버튼 누르면 되돌아가는 버튼 만들기
        binding.btnReSelect.setOnClickListener {
            val intent = Intent(requireContext(), MakingTourAddListActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }
}