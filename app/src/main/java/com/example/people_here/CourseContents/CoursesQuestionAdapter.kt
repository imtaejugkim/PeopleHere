package com.example.people_here.CourseContents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.CourseQuestionData
import com.example.people_here.databinding.ItemCourseContentsQuestionBinding

class CoursesQuestionAdapter(val questionData : ArrayList<CourseQuestionData>) : RecyclerView.Adapter<CoursesQuestionAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemCourseContentsQuestionBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(questionInfo : CourseQuestionData){
            binding.tvCourseQuestion.text = questionInfo.question
            binding.tvCourseAnswer.text = questionInfo.answer
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoursesQuestionAdapter.ViewHolder {
        val binding = ItemCourseContentsQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoursesQuestionAdapter.ViewHolder, position: Int) {
        holder.bind(questionData[position])
    }

    override fun getItemCount(): Int  = questionData.size
}