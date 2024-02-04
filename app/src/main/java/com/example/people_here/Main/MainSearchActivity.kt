package com.example.people_here.Main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.people_here.Data.MainSearchData
import com.example.people_here.R
import com.example.people_here.databinding.ActivityMainSearchBinding

class MainSearchActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainSearchBinding
    private var mainSearchData : ArrayList<MainSearchData> = arrayListOf()
    private var mainSearchRecentAdapter : MainSearchRecentAdapter?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("search시작","search")
        binding = ActivityMainSearchBinding.inflate(layoutInflater)

        //백앤드 통신 시 변경될 데이터 추가 방식입니다.
        initRecyclerView()
        initDummyData()

        binding.ivMainSearchArrow.setOnClickListener{
            initBackStack()
        }

        binding.etMainSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                initFilterData(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


        setContentView(binding.root)
    }

//    private fun initFilterData(query: String) {
//        val filteredList = if (query.isEmpty()) {
//            mainSearchData
//        } else {
//            mainSearchData.filter {
//                it.title.contains(query, ignoreCase = true)
//            }
//        }
//
//        mainSearchRecentAdapter?.updateData(ArrayList(filteredList))
//    }


    private fun initBackStack() {
        finish()
    }

    private fun initDummyData() {
        mainSearchData.addAll(
            arrayListOf(
                MainSearchData(R.drawable.img_example_place,"홍대입구","홍대개미"),
                MainSearchData(R.drawable.img_example_place,"건대입구","홍대개미"),
                MainSearchData(R.drawable.img_example_place,"이대입구","홍대개미"),
                MainSearchData(R.drawable.img_example_place,"중대입구","홍대개미"),
                MainSearchData(R.drawable.img_example_place,"서울대입구","홍대개미"),
                MainSearchData(R.drawable.img_example_place,"동국대입구","홍대개미"),
                MainSearchData(R.drawable.img_example_place,"세종대입구","홍대개미"),
                MainSearchData(R.drawable.img_example_place,"국민대입구","홍대개미"),
                MainSearchData(R.drawable.img_example_place,"경희대입구","홍대개미"),
                MainSearchData(R.drawable.img_example_place,"성대입구","홍대개미")
            )
        )
    }


    private fun initRecyclerView() {
        mainSearchRecentAdapter = MainSearchRecentAdapter(mainSearchData)
        binding.rvMainSearchRecent.adapter = mainSearchRecentAdapter
        binding.rvMainSearchRecent.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
    }

}