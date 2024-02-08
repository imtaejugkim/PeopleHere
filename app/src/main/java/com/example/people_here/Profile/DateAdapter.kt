package com.example.people_here.Profile

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.CalendarData
import com.example.people_here.R
import com.example.people_here.databinding.ItemCalendarDateBinding

class DateAdapter(val dayList : CalendarData) : RecyclerView.Adapter<DateAdapter.ViewHolder>() {
    var dateClickListener: OnDateClickListener? = null
    private var selectedPosition = -1

    interface OnDateClickListener {
        fun onDateClick(date: String)
    }


    inner class ViewHolder(val binding : ItemCalendarDateBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : String){
            binding.tv.text = item
            binding.root.setOnClickListener{
                selectedPosition = adapterPosition
                notifyDataSetChanged()
                dateClickListener?.onDateClick(item)
            }

            if (adapterPosition == selectedPosition) {
                binding.clCalendarDay.setBackgroundResource(R.drawable.calendar_gray6_12)
                binding.tv.setTextColor(Color.WHITE)
            } else {
                binding.root.setBackgroundColor(Color.TRANSPARENT) // 기본 배경
                binding.tv.setTextColor(Color.BLACK)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCalendarDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dayList.day[position])
    }

    override fun getItemCount() : Int{
        return dayList.day.size
    }
}
