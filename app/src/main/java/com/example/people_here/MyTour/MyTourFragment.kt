package com.example.people_here.MyTour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.people_here.databinding.FragmentMyTourBinding

class MyTourFragment : Fragment() {
    private lateinit var binding : FragmentMyTourBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyTourBinding.inflate(layoutInflater)

        return binding.root
    }


}