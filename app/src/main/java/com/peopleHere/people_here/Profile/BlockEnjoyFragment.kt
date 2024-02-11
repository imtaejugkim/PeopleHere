package com.peopleHere.people_here.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peopleHere.people_here.databinding.FragmentBlockEnjoyBinding

class BlockEnjoyFragment : Fragment() {
    lateinit var binding : FragmentBlockEnjoyBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlockEnjoyBinding.inflate(layoutInflater)


        return binding.root
    }

}
