package com.peopleHere.people_here.Main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.Data.MainCategoryData
import com.peopleHere.people_here.databinding.ItemCalendarDateBinding
import com.peopleHere.people_here.databinding.ItemMainCategoryBinding
import com.peopleHere.people_here.databinding.ItemMainTourListCategoryBinding

class MainCategoryAdapter(val mainCategoryData: List<String>) : RecyclerView.Adapter<MainCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : ItemMainCategoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : String){
            binding.tvMainTourListCategory.text = item
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainCategoryAdapter.ViewHolder {
        val binding = ItemMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainCategoryAdapter.ViewHolder, position: Int) {
        holder.bind(mainCategoryData[position])
    }

    override fun getItemCount(): Int = mainCategoryData.size
}