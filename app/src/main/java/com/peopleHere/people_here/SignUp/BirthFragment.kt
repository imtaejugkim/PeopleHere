package com.peopleHere.people_here.SignUp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.util.findColumnIndexBySuffix
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.peopleHere.people_here.databinding.FragmentBirthBinding

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
            //data 넘기기 잘 담겨지긴 하데 넘기는게 안되네용?
            Log.d("year",binding.npYearPicker.value.toString())
            intent.putExtra("year", binding.npYearPicker.value.toString())
            intent.putExtra("month", binding.npMonthPicker.value.toString())
            intent.putExtra("day", binding.npDayPicker.value.toString())
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.remove(this)
            transaction.commit()            //이렇게 하면 넘겨지지 않나~
        }


        return binding.root


    }


}