package com.peopleHere.people_here.Profile

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.Data.CalendarData
import com.peopleHere.people_here.Main.MainAdapter
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ItemCalendarDateBinding

class DateAdapter(val dayList : CalendarData, val month : Int, val year : Int, private var dateClickListener: OnDateClickListener) : RecyclerView.Adapter<DateAdapter.ViewHolder>() {
    private var selectedPosition = -1

    interface OnDateClickListener {
        fun onDateClick(date: String, month: Int, year: Int)
    }


    inner class ViewHolder(val binding : ItemCalendarDateBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : String) {
            binding.tv.text = item
            if (item!="") {
                binding.root.setOnClickListener {
                    selectedPosition = adapterPosition
                    notifyDataSetChanged()
                    dateClickListener.onDateClick(item, month, year)
                }

                if (adapterPosition == selectedPosition) {
                    binding.clCalendarDay.setBackgroundResource(R.drawable.calendar_gray6_12)
                    binding.tv.setTextColor(Color.WHITE)
                } else {
                    binding.root.setBackgroundColor(Color.TRANSPARENT) // 기본 배경
                    binding.tv.setTextColor(Color.BLACK)

                }
            }else{
                binding.root.setOnClickListener(null)
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

    fun setOnDateClickListener(onDateClickListener : OnDateClickListener){
        dateClickListener = onDateClickListener
    }

    override fun getItemCount() : Int = dayList.day.size

}
