package com.peopleHere.people_here.CourseContents

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peopleHere.people_here.Data.CourseReviewData
import com.peopleHere.people_here.databinding.ItemCourseContentsReviewBinding

class CourseReviewAdapter(val context : Context, private val reviewData: ArrayList<CourseReviewData>) : RecyclerView.Adapter<CourseReviewAdapter.ViewHolder>(){
    inner class ViewHolder(val binding : ItemCourseContentsReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(reviewInfo : CourseReviewData){
            binding.tvUserName.text = reviewInfo.userName
            binding.tvUserNickname.text = reviewInfo.userNickName
            Glide.with(context)
                .load(reviewInfo.userImage)
                .into(binding.ivReviewImage)
            binding.tvReviewMonth.text = reviewInfo.reviewMonth.toString()
            binding.tvReviewYear.text = reviewInfo.reviewYear.toString()
            binding.tvReview.text = reviewInfo.reviewText
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseReviewAdapter.ViewHolder {
        val binding = ItemCourseContentsReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseReviewAdapter.ViewHolder, position: Int) {
        holder.bind(reviewData[position])
    }

    override fun getItemCount(): Int = reviewData.size
}