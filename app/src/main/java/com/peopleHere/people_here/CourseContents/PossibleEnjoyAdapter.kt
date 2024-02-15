package com.peopleHere.people_here.CourseContents

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.Data.MainData
import com.peopleHere.people_here.Remote.UpcomingDateResponse
import com.peopleHere.people_here.databinding.ItemPossibleEnjoyDateBinding
import java.text.SimpleDateFormat
import java.util.Locale

class PossibleEnjoyAdapter(private val classifiedData: Map<String, ArrayList<UpcomingDateResponse>>,
                           val tourTime : Int,
                           val enjoyClickListener : PossibleEnjoyInnerAdapter.OnItemClickListener)
    : RecyclerView.Adapter<PossibleEnjoyAdapter.ViewHolder>(){

    private lateinit var itemClickListener: OnItemClickListener
    private val months = classifiedData.keys.toList()

    interface OnItemClickListener{
        fun onItemClick(dateInfo: UpcomingDateResponse)
    }

    inner class ViewHolder(val binding: ItemPossibleEnjoyDateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(month: String) {
            val inputFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy년 M월", Locale.getDefault())
            val date = inputFormat.parse(month)
            val formattedDate = outputFormat.format(date)
            binding.tvDate.text = formattedDate

            val monthlyData = classifiedData[month] ?: arrayListOf()
            val innerAdapter = PossibleEnjoyInnerAdapter(monthlyData, tourTime, enjoyClickListener)
            binding.rvDate.layoutManager = LinearLayoutManager(binding.root.context,
                LinearLayoutManager.VERTICAL, false)
            binding.rvDate.adapter = innerAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPossibleEnjoyDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val month = months[position]
        holder.bind(month)
    }

    override fun getItemCount(): Int = months.size

    fun setOnItemClickListener(onItemClickListener : OnItemClickListener){
        itemClickListener = onItemClickListener
    }

}