package com.peopleHere.people_here.Main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.Data.MainCategoryData
import com.peopleHere.people_here.databinding.ItemMainTourListCategoryBinding

class MainTourCategoryAdapter(val mainCategoryData: List<String>) : RecyclerView.Adapter<MainTourCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : ItemMainTourListCategoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(categoryData : MainCategoryData){
            binding.tvMainTourListCategory.text = categoryData.category.toString()
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainTourCategoryAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MainTourCategoryAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}