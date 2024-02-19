package com.peopleHere.people_here.Profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peopleHere.people_here.R
import com.peopleHere.people_here.Remote.BringCourseResponse
import com.peopleHere.people_here.databinding.ItemDayTripBinding
import com.peopleHere.people_here.databinding.ItemMainCategoryBinding

class CategoryTripAdapter(val categoryName : List<String>) : RecyclerView.Adapter<CategoryTripAdapter.ViewHolder>() {
    private lateinit var itemClickListener : OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(dayTripInfo : BringCourseResponse)
    }


    inner class ViewHolder(val binding : ItemMainCategoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : String) {
            binding.tvMainTourListCategory.text = item
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryName[position])
    }

    fun setOnItemClickListener(onItemClickListener : OnItemClickListener){
        itemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int {
        return categoryName.size
    }



}
