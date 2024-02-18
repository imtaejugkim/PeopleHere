package com.peopleHere.people_here.Profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.peopleHere.people_here.Data.MainData
import com.peopleHere.people_here.Data.ProfileData
import com.peopleHere.people_here.Remote.AuthService
import com.peopleHere.people_here.Remote.ProfileView

import com.peopleHere.people_here.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(), ProfileView {
    private lateinit var binding: FragmentProfileBinding
    private var profiledata: ProfileData? = null
    private var profileAdapter: ProfileAdapter? = null
    private lateinit var authService: AuthService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        authService = AuthService(requireContext())
        authService.ProfileView(this)
        //이거 authservice.함수 해서 받아와서 성공하면 그대로 넣기 하자 SignINnView처럼
        authService.profileInfo()

        //이러고 maininfo받고 adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun ProfileSuccess(content: ProfileData) {
        profiledata = content
        // profiledata가 초기화된 후에 adapter를 생성합니다.
        profileAdapter = ProfileAdapter(profiledata!!, requireContext())
        binding.recyclerView.adapter = profileAdapter
        Log.d("datacheck",profiledata.toString())
    }

    override fun MainFailure(status: Int, message: String) {
        TODO("Not yet implemented")
    }


}