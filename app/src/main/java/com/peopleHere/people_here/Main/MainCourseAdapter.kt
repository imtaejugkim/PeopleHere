package com.peopleHere.people_here.Main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.Data.MainCourseData
import com.peopleHere.people_here.databinding.ItemMainTourListCourse1Binding
import com.peopleHere.people_here.databinding.ItemMainTourListCourse2Binding

class MainCourseAdapter(private val mainCourseData: ArrayList<MainCourseData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_FIRST_ITEM = 0
        private const val TYPE_OTHER_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_FIRST_ITEM else TYPE_OTHER_ITEM
    }

    inner class FirstItemViewHolder(val binding: ItemMainTourListCourse1Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tourListInfo: MainCourseData) {
            binding.ivMainTourListUser.setImageResource(tourListInfo.userImage)
            binding.tvMainTourListUser.text = tourListInfo.userName
        }
    }

    inner class OtherItemViewHolder(val binding: ItemMainTourListCourse2Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tourListInfo: MainCourseData) {

            binding.ivMainTourListUserCourse.setImageResource(tourListInfo.courseImage)
            binding.tvItemMainTourListRegion.text = tourListInfo.courseRegion
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_FIRST_ITEM -> {
                val binding = ItemMainTourListCourse1Binding.inflate(inflater, parent, false)
                FirstItemViewHolder(binding)
            }
            else -> {
                val binding = ItemMainTourListCourse2Binding.inflate(inflater, parent, false)
                OtherItemViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mainCourseData[position]
        when (holder) {
            is FirstItemViewHolder -> holder.bind(item)
            is OtherItemViewHolder -> holder.bind(item)
        }
    }

    override fun getItemCount(): Int = mainCourseData.size
}
