package com.example.people_here.SignUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.people_here.R
import com.example.people_here.databinding.FragmentBirthBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BirthFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentBirthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentBirthBinding.inflate(layoutInflater)

        binding.npYearPicker.maxValue=2024
        binding.npYearPicker.minValue=1940
        binding.npMonthPicker.maxValue=12
        binding.npMonthPicker.minValue=1
        binding.npDayPicker.maxValue=31
        binding.npDayPicker.minValue=1








        return binding.root


    }


}