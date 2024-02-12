package com.peopleHere.people_here.CourseContents

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peopleHere.people_here.Data.CourseReviewData
import com.peopleHere.people_here.Data.CourseScheduleData
import com.peopleHere.people_here.databinding.ItemCourseComingDateBinding
import com.peopleHere.people_here.databinding.ItemCourseContentsReviewBinding
import com.peopleHere.people_here.databinding.ItemCourseContentsReviewInnerBinding

class CourseComingDateAdapter(private val reviewData: ArrayList<CourseScheduleData>) : RecyclerView.Adapter<CourseComingDateAdapter.ViewHolder>(){
    inner class ViewHolder(val binding : ItemCourseComingDateBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(reviewInfo : CourseScheduleData){

        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseComingDateAdapter.ViewHolder {
        val binding = ItemCourseComingDateBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseComingDateAdapter.ViewHolder, position: Int) {
        holder.bind(reviewData[position])
    }

    override fun getItemCount(): Int = reviewData.size
}