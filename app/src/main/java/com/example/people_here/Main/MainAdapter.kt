package com.example.people_here.Main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.MainData
import com.example.people_here.R
import com.example.people_here.databinding.ItemMainTourListBinding

class MainAdapter(val mainData : ArrayList<MainData>) : RecyclerView.Adapter<MainAdapter.ViewHolder>(){
    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var heartClickListener : OnHeartClickListener

    interface OnItemClickListener{
        fun onItemClick(tourListInfo: MainData)
    }

    interface OnHeartClickListener{
        fun onHeartClick(position : Int)
    }

    inner class ViewHolder(val binding : ItemMainTourListBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(tourListInfo : MainData){
            binding.tvMainTourListTitle.text = tourListInfo.mainTourListTitle
            binding.tvMainTourListTime.text = tourListInfo.mainTourListTime
            binding.clItemMainTourList.setOnClickListener {
                itemClickListener.onItemClick(tourListInfo)
            }

            binding.icMainTourHeart.setImageResource(initHeartImage(tourListInfo))
            binding.icMainTourHeart.setOnClickListener {
                heartClickListener.onHeartClick(adapterPosition)
            }

            binding.tvMainTourListLocation.text = initLocationText(tourListInfo)

            // 내부 RecyclerView 초기화 및 어댑터 설정
            val innerAdapter = MainCourseAdapter(tourListInfo.mainTourListCourses) // 가정: MainTourListData에 내부 리스트 데이터가 포함됨
            binding.rvMainTourListCourse.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvMainTourListCourse.adapter = innerAdapter
        }
    }

    private fun initHeartImage(tourListInfo: MainData) : Int {
        val heartIconResId = if (tourListInfo.mainTourHeart) {
            R.drawable.ic_main_filled_heart
        } else {
            R.drawable.ic_main_empty_heart
        }

        return heartIconResId
    }

    private fun initLocationText(tourListInfo: MainData): String {
        val locationText = when {
            tourListInfo.mainTourListRegion.size > 1 -> "${tourListInfo.mainTourListRegion[0]} 외 ${tourListInfo.mainTourListRegion.size - 1}개"
            tourListInfo.mainTourListRegion.isNotEmpty() -> tourListInfo.mainTourListRegion[0]
            else -> "Null지역"
        }
        return locationText
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainAdapter.ViewHolder {
        val binding = ItemMainTourListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.bind(mainData[position])
    }

    fun setOnItemClickListener(onItemClickListener : OnItemClickListener){
        itemClickListener = onItemClickListener
    }

    fun setOnHeartClickListener(onHeartClickListener : OnHeartClickListener){
        heartClickListener = onHeartClickListener
    }

    override fun getItemCount(): Int = mainData.size
}