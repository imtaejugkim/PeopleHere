package com.peopleHere.people_here.Profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peopleHere.people_here.Data.MainData
import com.peopleHere.people_here.Data.ProfileData
import com.peopleHere.people_here.Local.getJwt
import com.peopleHere.people_here.Main.MainAdapter
import com.peopleHere.people_here.Remote.AuthService

import com.peopleHere.people_here.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var profiledata : ArrayList<ProfileData> = arrayListOf()
   // private var profileAdapter : ProfileAdapter?= null
    private lateinit var authService: AuthService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)



        return binding.root
    }


}