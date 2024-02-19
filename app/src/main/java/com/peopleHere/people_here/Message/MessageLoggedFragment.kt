package com.peopleHere.people_here.Message

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.peopleHere.people_here.Data.ChatData
import com.peopleHere.people_here.Data.ProfileData
import com.peopleHere.people_here.Profile.ProfileAdapter
import com.peopleHere.people_here.Profile.ProfilePastAdapter
import com.peopleHere.people_here.R
import com.peopleHere.people_here.Remote.AuthService
import com.peopleHere.people_here.Remote.MessageView
import com.peopleHere.people_here.databinding.FragmentMessageLoggedBinding
import com.peopleHere.people_here.databinding.FragmentProfileBinding

class MessageLoggedFragment : Fragment(), MessageView {
    private lateinit var binding: FragmentMessageLoggedBinding
    private var chatData: ArrayList<ChatData>? = null
    private var messageAdapter: MessageAdapter? = null

    private lateinit var authService: AuthService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageLoggedBinding.inflate(layoutInflater)
        //TODO:이건 실행되는데 왜 함수가 안돌지??
        authService = AuthService(requireContext())
        authService.MessageView(this)

        authService.chatUpdate(17)

        binding.rvMessage.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        return binding.root


    }


    override fun MessageSuccess(content: ArrayList<ChatData>) {
        chatData = content
        messageAdapter = MessageAdapter(chatData!!)
        binding.rvMessage.adapter = messageAdapter
    }

    override fun MessageFailure(status: Int, message: String) {
        TODO("Not yet implemented")
    }
}