package com.peopleHere.people_here.Main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.peopleHere.people_here.Data.MainData
import com.peopleHere.people_here.CourseContents.CourseContentsActivity
import com.peopleHere.people_here.databinding.FragmentMainBinding
import com.peopleHere.people_here.Local.getJwt
import com.peopleHere.people_here.Remote.AuthService
import com.peopleHere.people_here.Remote.ChangeWishView
import com.peopleHere.people_here.Remote.MainView

class MainFragment : Fragment() , MainView, ChangeWishView {
    private lateinit var binding: FragmentMainBinding
    private var mainData : ArrayList<MainData> = arrayListOf()
    private var mainAdapter : MainAdapter?= null
    private lateinit var authService: AuthService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        authService = AuthService()

        //백앤드 통신 시 변경될 데이터 추가 방식입니다.
        initRecyclerView()
        initDataManager()

        binding.clTopSearchBar.setOnClickListener {
            initSearchView()
        }

        binding.clSeeMap.setOnClickListener {
            val intent = Intent(requireActivity(), MainCourseMapActivity::class.java)
//                intent.putExtra("Key", tourListInfo)
            startActivity(intent)
        }

        return binding.root
    }

    private fun initDataManager() {
        val token = getJwt()
        Log.d("token",token)
        if(token.isNotEmpty()){

            val authService = AuthService(requireContext())
            authService.setMainView(this)
            authService.setChangeWishView(this)
            authService.mainInfo()
        }else{
            Log.d("token 오류","token 오류")
        }
    }

    private fun initRecyclerView() {
        mainAdapter = MainAdapter(mainData)
        binding.rvMainTourList.adapter = mainAdapter
        binding.rvMainTourList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        mainAdapter!!.setOnItemClickListener(object : MainAdapter.OnItemClickListener{
            override fun onItemClick(tourListInfo : MainData) {
                val intent = Intent(requireActivity(), CourseContentsActivity::class.java)
                Log.d("tourId",tourListInfo.tourId.toString())
                intent.putExtra("key", tourListInfo.tourId)
                startActivity(intent)
            }
        })

        mainAdapter!!.setOnHeartClickListener(object : MainAdapter.OnHeartClickListener {
            override fun onHeartClick(position: Int) {
                val tourId = mainData[position].tourId
                val item = mainData[position]
                item.wished = !item.wished

                mainAdapter!!.notifyItemChanged(position)
                authService.changeWishInfo(tourId)

                Log.d("Heart Clicked", "Tour ID: $tourId")
            }
        })
    }

    private fun initSearchView() {
        val intent = Intent(requireContext(), MainSearchActivity::class.java)
        startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
        binding.root.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        binding.root.visibility = View.VISIBLE
    }

    override fun MainLoading() {
        TODO("Not yet implemented")
    }

    override fun MainSuccess(content : ArrayList<MainData>) {
        mainData.clear() // 기존 데이터를 클리어
        mainData.addAll(content) // 새로운 데이터 추가

        // 어댑터에 데이터 변경 알림
        mainAdapter?.notifyDataSetChanged()
    }

    override fun MainFailure(status: Int, message: String) {
        Log.d("메인에러1",status.toString())
        Log.d("메인에러2",message)
    }

    override fun ChangeWishLoading() {
        TODO("Not yet implemented")
    }

    override fun ChangeWishSuccess() {
        Log.d("위시성공","위시성공!")
    }

    override fun ChangeWishFailure(status: Int, message: String) {
        Log.d("위시에러1",status.toString())
        Log.d("위시에러2",message)
    }
}
