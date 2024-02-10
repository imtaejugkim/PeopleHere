package com.example.people_here.SignUp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.util.findColumnIndexBySuffix
import com.example.people_here.Login.LoginEmailNextActivity
import com.example.people_here.R
import com.example.people_here.databinding.FragmentBirthBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BirthFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBirthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBirthBinding.inflate(layoutInflater)

        binding.npYearPicker.maxValue = 2024
        binding.npYearPicker.minValue = 1940
        binding.npMonthPicker.maxValue = 12
        binding.npMonthPicker.minValue = 1
        binding.npDayPicker.maxValue = 31
        binding.npDayPicker.minValue = 1
        //여기다시

        binding.cvOk.setOnClickListener {
            val intent = Intent(requireContext(), SignUpActivity::class.java)
            //data 넘기기
            intent.putExtra("year", binding.npYearPicker.value.toString())
            intent.putExtra("month", binding.npMonthPicker.value.toString())
            intent.putExtra("day", binding.npDayPicker.value.toString())
            dismiss()
            //이렇게 하면 넘겨지지 않나~
        }


        return binding.root


    }


}