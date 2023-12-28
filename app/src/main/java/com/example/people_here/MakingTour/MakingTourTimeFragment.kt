package com.example.people_here.MakingTour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.people_here.databinding.FragmentMakingTourTimeBinding

class MakingTourTimeFragment : Fragment() {
    lateinit var binding : FragmentMakingTourTimeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMakingTourTimeBinding.inflate(layoutInflater)
        return binding.root
    }
}