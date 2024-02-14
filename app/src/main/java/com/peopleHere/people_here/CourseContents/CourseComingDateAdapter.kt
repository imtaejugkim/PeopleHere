package com.peopleHere.people_here.CourseContents

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peopleHere.people_here.Data.CourseReviewData
import com.peopleHere.people_here.Data.CourseScheduleData
import com.peopleHere.people_here.Remote.CourseContentsResponse
import com.peopleHere.people_here.Remote.UpcomingDateResponse
import com.peopleHere.people_here.databinding.ItemCourseComingDateBinding
import com.peopleHere.people_here.databinding.ItemCourseContentsReviewBinding
import com.peopleHere.people_here.databinding.ItemCourseContentsReviewInnerBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CourseComingDateAdapter(val courseData : CourseContentsResponse, private val comingData: ArrayList<UpcomingDateResponse>) : RecyclerView.Adapter<CourseComingDateAdapter.ViewHolder>(){
    inner class ViewHolder(val binding : ItemCourseComingDateBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(comingInfo: UpcomingDateResponse) {
            //날짜 환산
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("yyyy년 M월 d일 (E)", Locale.getDefault())
            val date = inputDateFormat.parse(comingInfo.date) ?: return
            binding.tvComingYear.text = outputDateFormat.format(date)

            //시간 환산
            if(comingInfo.time == null){
                binding.llTimeExist.visibility = View.GONE
                binding.tvTimeNone.visibility = View.VISIBLE
            }else {
                binding.llTimeExist.visibility = View.VISIBLE
                binding.tvTimeNone.visibility = View.GONE

                val inputDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                val outputDateFormat = SimpleDateFormat("a hh:mm", Locale.getDefault())
                val startTime = inputDateFormat.parse(comingInfo.time)
                val calendar = Calendar.getInstance().apply {
                    time = startTime ?: return
                    add(Calendar.MINUTE, courseData.time)
                }
                val endTime = calendar.time

                binding.tvTimeExistFirst.text = outputDateFormat.format(startTime)
                binding.tvTimeExistSecond.text = outputDateFormat.format(endTime)
            }
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
        holder.bind(comingData[position])
    }

    override fun getItemCount(): Int = comingData.size
}