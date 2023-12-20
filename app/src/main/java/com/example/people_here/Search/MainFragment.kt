package com.example.people_here.Search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.example.people_here.R
import com.example.people_here.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.vpMainTourSelect.adapter = TabLayoutVPAdapter(this)

        TabLayoutMediator(binding.tlMainTourSelect, binding.vpMainTourSelect) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.main_tour_list)
                1 -> tab.text = getString(R.string.main_tour_course)
            }

            val textSize = resources.getDimension(R.dimen.main_tour_list)
            tab.view.forEach { view ->
                if (view is TextView) {
                    view.textSize = textSize
                    view.setTextColor(ContextCompat.getColor(requireContext(), R.color.Gray8))
                }
            }
        }.attach()
    }
}
