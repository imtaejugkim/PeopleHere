package com.peopleHere.people_here.Profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.peopleHere.people_here.Data.MainData
import com.peopleHere.people_here.Data.ProfileData
import com.peopleHere.people_here.R
import com.peopleHere.people_here.Remote.AuthService
import com.peopleHere.people_here.Remote.ProfileView

import com.peopleHere.people_here.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(), ProfileView {
    private lateinit var binding: FragmentProfileBinding
    private var profiledata: ProfileData? = null
    private var profileAdapter: ProfileAdapter? = null
    private var profilePastAdapter: ProfilePastAdapter? = null

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
        binding.recyclerViewPast.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        return binding.root
    }

    override fun ProfileSuccess(content: ProfileData) {
        profiledata = content
        profileAdapter = ProfileAdapter(profiledata!!, requireContext())
        profilePastAdapter= ProfilePastAdapter(profiledata!!,requireContext())

        binding.recyclerView.adapter = profileAdapter
        binding.recyclerViewPast.adapter = profilePastAdapter


        binding.cvTvName.text=content.currentUserInfo.userName

        content.currentUserInfo?.userImageUrl?.let { imageUrl ->
            Glide.with(this)
                .load(Uri.parse(imageUrl))
                .into(binding.ivProfileImg) // ImageView에 이미지를 표시합니다.
        } ?: run {
            // 이미지 URL이 null인 경우 기본 이미지를 표시합니다.
            Glide.with(this)
                .load(R.drawable.profile_olivia)
                .into(binding.ivProfileImg)
        }

        Log.d("datacheck",profiledata.toString())
    }

    override fun MainFailure(status: Int, message: String) {
        TODO("Not yet implemented")
    }


}