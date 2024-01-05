package com.example.people_here.CostInput

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.PlaceCostData
import com.example.people_here.R
import com.example.people_here.databinding.ItemActivityCostInputBinding

class PlaceCostAdapter(val placelist: ArrayList<PlaceCostData>) :
    RecyclerView.Adapter<PlaceCostAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(placelist: PlaceCostData)

    }

    inner class ViewHolder(val binding: ItemActivityCostInputBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(placelist: PlaceCostData) {
            binding.ivRegionImage.setImageResource(R.drawable.img)//여기 서버에서 받아오는 이미지로 대체
            binding.tvRegion.text = placelist.name
            binding.btnInputCost.setOnClickListener {
                itemClickListener.onItemClick(placelist)//하나의 객체 눌리게
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceCostAdapter.ViewHolder {
        val view =
            ItemActivityCostInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceCostAdapter.ViewHolder, position: Int) {
        holder.bind(placelist[position])
    }

    override fun getItemCount(): Int {
        return placelist.size
    }
}