package com.peopleHere.people_here.Message

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peopleHere.people_here.AddPicture.CustomAlbumActivity
import com.peopleHere.people_here.Login.LoginEmailActivity
import com.peopleHere.people_here.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {

    private lateinit var binding : FragmentMessageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentMessageBinding.inflate(layoutInflater)
        binding.btnYes.setOnClickListener {
            val intent = Intent(requireContext(), LoginEmailActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}