package com.peopleHere.people_here.Main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.Data.MainCourseMapData
import com.peopleHere.people_here.databinding.ItemMainCourseMapBinding

class MainTourCourseAdapter(val mainCourseMapData : ArrayList<MainCourseMapData>) : RecyclerView.Adapter<MainTourCourseAdapter.ViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    inner class ViewHolder(val binding : ItemMainCourseMapBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(courseMapInfo : MainCourseMapData){
            binding.tvMainCourseListTitle.text = courseMapInfo.courseTitle
            binding.tvMainCourseListRegion.text = courseMapInfo.courseRegion.toString()
            binding.tvMainCourseListTime.text = courseMapInfo.courseTime.toString()
            binding.tvMainCourseListUserName.text = courseMapInfo.userName
            binding.ivMainCourseListUser.setImageResource(courseMapInfo.userImage)
            binding.ivMainCourseListUserCourse.setImageResource(courseMapInfo.placeImage)
            binding.tvItemMainCourseListPlace.text = courseMapInfo.placeName
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainTourCourseAdapter.ViewHolder {
        val binding = ItemMainCourseMapBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainTourCourseAdapter.ViewHolder, position: Int) {
        holder.bind(mainCourseMapData[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(holder.itemView, position)
        }
    }

    override fun getItemCount(): Int = mainCourseMapData.size

    fun setData(data: List<MainCourseMapData>) {
        mainCourseMapData.clear()
        mainCourseMapData.addAll(data)
        notifyDataSetChanged()
    }
}