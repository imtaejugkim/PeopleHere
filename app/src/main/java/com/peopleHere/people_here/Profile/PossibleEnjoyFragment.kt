package com.peopleHere.people_here.Profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peopleHere.people_here.databinding.FragmentPossibleEnjoyBinding


class PossibleEnjoyFragment : Fragment() {
    lateinit var binding : FragmentPossibleEnjoyBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPossibleEnjoyBinding.inflate(layoutInflater)

        val date = arguments?.getString("date") ?: ""
        val month = arguments?.getInt("month") ?: 0
        val year = arguments?.getInt("year") ?: 0


        binding.btnPossibleEnjoy.setOnClickListener {
            val intent = Intent(requireActivity(), SetTimeActivity()::class.java)
            intent.putExtra("date",date)
            intent.putExtra("month",month)
            intent.putExtra("year",year)
            startActivity(intent)
        }

        return binding.root
    }

}
