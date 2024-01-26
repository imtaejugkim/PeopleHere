package com.example.people_here.Search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.people_here.Data.MainTourListCourseData
import com.example.people_here.Data.MainTourListData
import com.example.people_here.R
import com.example.people_here.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private var mainTourListData : ArrayList<MainTourListData> = arrayListOf()
    private var mainTourListAdapter : MainTourListAdapter?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)

        //백앤드 통신 시 변경될 데이터 추가 방식입니다.
        initRecyclerView()
        initDummyData()


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


    private fun initDummyData() {
        mainTourListData.addAll(
            arrayListOf(
                MainTourListData("홍대 소품삽 둘러보기","1시간","10000원", createDummyInnerData(0)),
                MainTourListData("건대 소품삽 둘러보기","2시간","20000원", createDummyInnerData(1)),
                MainTourListData("이대 소품삽 둘러보기","3시간","30000원", createDummyInnerData(2)),
                MainTourListData("중대 소품삽 둘러보기","4시간","40000원", createDummyInnerData(index = 3)),
                MainTourListData("고대 소품삽 둘러보기","5시간","5000원", createDummyInnerData(index = 4)),
                MainTourListData("연대 소품삽 둘러보기","6시간","60000원", createDummyInnerData(index = 5)),
                MainTourListData("성대 소품삽 둘러보기","7시간","70000원", createDummyInnerData(index = 6)),
                MainTourListData("서강대 소품삽 둘러보기","8시간","80000원", createDummyInnerData(index = 7)),
                MainTourListData("경희대 소품삽 둘러보기","9시간","90000원", createDummyInnerData(index = 8)),
                MainTourListData("시립대 소품삽 둘러보기","10시간","100000원", createDummyInnerData(index = 9))
            )
        )

    }

    private fun createDummyInnerData(index: Int): ArrayList<MainTourListCourseData> {
        val dataList = ArrayList<MainTourListCourseData>()
        for (i in 1..10) {
            val userName = "홍길동${index * 10 + i}"
            val userRating = (index + i) % 5 + 1.0 // 예시로 평점 계산
            dataList.add(
                MainTourListCourseData(
                    R.drawable.img_example_user,
                    userName,
                    userRating,
                    R.drawable.img_example,
                    "홍대역")
            )
        }
        return dataList
    }

    private fun initRecyclerView() {
        mainTourListAdapter = MainTourListAdapter(mainTourListData)
        binding.rvMainTourList.adapter = mainTourListAdapter
        binding.rvMainTourList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        mainTourListAdapter!!.setOnItemClickListener(object : MainTourListAdapter.OnItemClickListener{
            override fun onItemClick(tourListInfo : MainTourListData) {
                val intent = Intent(requireActivity(), MainCourseMapActivity::class.java)
//                intent.putExtra("Key", tourListInfo)
                startActivity(intent)
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
}
