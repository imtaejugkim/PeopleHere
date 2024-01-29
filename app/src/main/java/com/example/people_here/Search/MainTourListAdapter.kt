package com.example.people_here.Search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.MainTourListData
import com.example.people_here.databinding.ItemMainTourListBinding

class MainTourListAdapter(val mainTourListData : ArrayList<MainTourListData>) : RecyclerView.Adapter<MainTourListAdapter.ViewHolder>(){
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(tourListInfo: MainTourListData)
    }

    inner class ViewHolder(val binding : ItemMainTourListBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(tourListInfo : MainTourListData){
            binding.tvMainTourListTitle.text = tourListInfo.mainTourListTitle
            binding.tvMainTourListTime.text = tourListInfo.mainTourListTime
            binding.clItemMainTourList.setOnClickListener {
                itemClickListener.onItemClick(tourListInfo)
            }
            val locationText = when {
                tourListInfo.mainTourListRegion.size > 1 -> "${tourListInfo.mainTourListRegion[0]} 외 ${tourListInfo.mainTourListRegion.size - 1}개"
                tourListInfo.mainTourListRegion.isNotEmpty() -> tourListInfo.mainTourListRegion[0]
                else -> "Null지역"
            }
            binding.tvMainTourListLocation.text = locationText.toString()

            // 내부 RecyclerView 초기화 및 어댑터 설정
            val innerAdapter = MainTourListCourseAdapter(tourListInfo.mainTourListCourses) // 가정: MainTourListData에 내부 리스트 데이터가 포함됨
            binding.rvMainTourListCourse.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvMainTourListCourse.adapter = innerAdapter
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainTourListAdapter.ViewHolder {
        val binding = ItemMainTourListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainTourListAdapter.ViewHolder, position: Int) {
        holder.bind(mainTourListData[position])
    }

    fun setOnItemClickListener(onItemClickListener : OnItemClickListener){
        itemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int = mainTourListData.size
}