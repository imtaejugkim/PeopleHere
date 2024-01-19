package com.example.people_here.CostInput

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.people_here.Data.PlaceCostData
import com.example.people_here.R
import com.example.people_here.databinding.ActivityCostInputBinding

class CostInputActivity : AppCompatActivity() {
    private var placeCostAdapter: PlaceCostAdapter? = null
    private lateinit var binding: ActivityCostInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCostInputBinding.inflate(layoutInflater)



        //임의로 테스트 위해 버튼 누르면 넘어가게



        usingAdapter()
        setContentView(binding.root)

    }

    private fun usingAdapter() {

        val placelist = arrayListOf(
            PlaceCostData(R.drawable.img,"장소1"),
            PlaceCostData(R.drawable.img,"장소2"),
            PlaceCostData(R.drawable.img,"장소3"),
            PlaceCostData(R.drawable.img,"장소4"),
            PlaceCostData(R.drawable.img,"장소5")
        )
        placeCostAdapter = PlaceCostAdapter(placelist)

        binding.rvPlace.adapter=placeCostAdapter
        binding.rvPlace.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        placeCostAdapter!!.setOnItemClickListener(object : PlaceCostAdapter.OnItemClickListener {
            override fun onItemClick(placelist: PlaceCostData) {
                //issue:근데 rv에 걸어놨는데 왜 경비 입력 눌러야 들어가지지??신기하넴
                //TODO:이것도 막음
                /*val bottomsheet=CostFragment()
                bottomsheet.show(supportFragmentManager, bottomsheet.tag)
                *///여기 프래그먼트 적용
                //bottomdialog 나오게

            }
        })
    }


}