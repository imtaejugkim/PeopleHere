package com.example.people_here.Search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.people_here.R
import com.example.people_here.databinding.FragmentMainTourCourseBinding

class MainTourCourseFragment : Fragment() {
    lateinit var binding : FragmentMainTourCourseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainTourCourseBinding.inflate(layoutInflater)
        return binding.root
    }

}