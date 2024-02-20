package com.peopleHere.people_here.CourseContents

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peopleHere.people_here.Data.CourseReviewData
import com.peopleHere.people_here.Data.MainCourseData
import com.peopleHere.people_here.databinding.ItemContentsMapBinding
import com.peopleHere.people_here.databinding.ItemCourseContentsReviewBinding
import com.peopleHere.people_here.databinding.ItemMainSearchBinding

class ContentsMapAdapter(private val reviewData: ArrayList<MainCourseData>) : RecyclerView.Adapter<ContentsMapAdapter.ViewHolder>(){
    inner class ViewHolder(val binding : ItemContentsMapBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(reviewInfo : MainCourseData){
            binding.tvMainSearchRecentRegion.text = reviewInfo.placeName
            binding.tvMainSearchRecentPlace.text = reviewInfo.placeAddress

        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContentsMapAdapter.ViewHolder {
        val binding = ItemContentsMapBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentsMapAdapter.ViewHolder, position: Int) {
        holder.bind(reviewData[position])
    }

    override fun getItemCount(): Int = reviewData.size
}