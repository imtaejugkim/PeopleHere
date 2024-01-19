package com.example.people_here.MyTour

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.people_here.Search.MainSearchActivity
import com.example.people_here.databinding.FragmentMyTourBinding

class MyTourFragment : Fragment() {

    private lateinit var binding : FragmentMyTourBinding
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyTourBinding.inflate(layoutInflater)

        //장소 검색 페이지로 넘어가기 구현
        binding.btnAddPlace.setOnClickListener {
            val intent = Intent(requireContext(), MainSearchActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }


}