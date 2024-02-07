package com.example.people_here.Profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.databinding.ItemCalendarBinding

class DateAdapter(val day : ArrayList<String>) : RecyclerView.Adapter<DateAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : ItemCalendarBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : String){
            binding.tv.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(day[position])
    }

    override fun getItemCount() : Int{
        return day.size
    }
}
