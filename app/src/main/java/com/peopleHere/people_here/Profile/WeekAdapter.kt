package com.peopleHere.people_here.Profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.databinding.ItemCalendarWeekBinding

class WeekAdapter(val week : ArrayList<String>) : RecyclerView.Adapter<WeekAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : ItemCalendarWeekBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : String){
            binding.tv.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCalendarWeekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.bind(week[position])
    }

    override fun getItemCount() : Int{
        return week.size
    }
}
