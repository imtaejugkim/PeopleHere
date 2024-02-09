package com.example.people_here.Main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.people_here.CourseContents.CourseContentsActivity
import com.example.people_here.Data.MainData
import com.example.people_here.Local.getJwt
import com.example.people_here.Remote.AuthService
import com.example.people_here.Remote.MainView
import com.example.people_here.databinding.FragmentMainBinding

class MainFragment : Fragment() , MainView {
    private lateinit var binding: FragmentMainBinding
    private var mainData : ArrayList<MainData> = arrayListOf()
    private var mainAdapter : MainAdapter?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)

        //백앤드 통신 시 변경될 데이터 추가 방식입니다.
        initRecyclerView()
//        initDummyData()
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
            val authService = AuthService()
            authService.setMainView(this)
            authService.mainInfo()
        }else{
            Log.d("token 오류","token 오류")
        }
    }


    private fun initDummyData() {
        val array1 = arrayListOf<String>("성동구","강북구","팔달구")
        val array2 = arrayListOf<String>("영통구")
        val array3 = arrayListOf<String>("영통구","팔달구")

//        mainData.addAll(
//            arrayListOf(
//                MainData("홍대 소품삽 둘러보기","1시간",false,array1, createDummyInnerData(0)),
//                MainData("건대 소품삽 둘러보기","2시간",false,array2, createDummyInnerData(1)),
//                MainData("이대 소품삽 둘러보기","3시간",false,array3, createDummyInnerData(2)),
//                MainData("중대 소품삽 둘러보기","4시간",false,array1, createDummyInnerData(3)),
//                MainData("고대 소품삽 둘러보기","5시간",false,array2, createDummyInnerData(4)),
//                MainData("연대 소품삽 둘러보기","6시간",false,array3, createDummyInnerData(5)),
//                MainData("성대 소품삽 둘러보기","7시간",false,array1, createDummyInnerData(6)),
//                MainData("서강대 소품삽 둘러보기","8시간",false,array2, createDummyInnerData(7)),
//                MainData("경희대 소품삽 둘러보기","9시간",false,array3, createDummyInnerData(8)),
//                MainData("시립대 소품삽 둘러보기","10시간",false,array1, createDummyInnerData(9))
//            )
//        )

    }

//    private fun createDummyInnerData(index: Int): ArrayList<MainCourseData> {
//        val dataList = ArrayList<MainCourseData>()
//        for (i in 1..10) {
//            val userName = "홍길동${index * 10 + i}"
//            dataList.add(
//                MainCourseData(
//                    R.drawable.img_example_user,
//                    userName,
//                    R.drawable.img_example,
//                    "홍대역")
//            )
//        }
//        return dataList
//    }

    private fun initRecyclerView() {
        mainAdapter = MainAdapter(mainData)
        binding.rvMainTourList.adapter = mainAdapter
        binding.rvMainTourList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        mainAdapter!!.setOnItemClickListener(object : MainAdapter.OnItemClickListener{
            override fun onItemClick(tourListInfo : MainData) {
                val intent = Intent(requireActivity(), CourseContentsActivity::class.java)
//                intent.putExtra("Key", tourListInfo)
                startActivity(intent)
            }
        })

        mainAdapter!!.setOnHeartClickListener(object : MainAdapter.OnHeartClickListener{
            override fun onHeartClick(position: Int) {
                val item = mainAdapter!!.mainData[position]
//                item.mainTourHeart = !item.mainTourHeart
                mainAdapter!!.notifyItemChanged(position)
                Log.d("하트 item 변경됨",position.toString())
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
        Log.d("content",content.toString())

        // 어댑터에 데이터 변경 알림
        mainAdapter?.notifyDataSetChanged()
    }

    override fun MainFailure(code: Int, message: String) {
        Log.d("통신O에러1",code.toString())
        Log.d("통신O에러2",message)
    }
}
