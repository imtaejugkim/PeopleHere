package com.example.people_here.AddPicture

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.CostInput.PlaceCostAdapter
import com.example.people_here.Data.LocationChooseData
import com.example.people_here.Data.PlaceCostData
import com.example.people_here.R
import com.example.people_here.databinding.ItemActivityCostInputBinding
import com.example.people_here.databinding.ItemLocationChooseBinding

class LocationChooseAdapter(val locationlist: ArrayList<LocationChooseData>) :
    RecyclerView.Adapter<LocationChooseAdapter.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    private var selectedItemPosition:Int ?= null
    interface OnItemClickListener {
        fun onItemClick(locationlist: LocationChooseData)

    }

    inner class ViewHolder(val binding: ItemLocationChooseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(locationlist: LocationChooseData,position: Int) {
            binding.ivRegionImage.setImageResource(R.drawable.img)//여기 서버에서 받아오는 이미지로 대체
            binding.tvRegion.text = locationlist.locationName//이름
            binding.cvOuter.setOnClickListener {
                itemClickListener.onItemClick(locationlist)//하나의 객체 눌리게


                val orange3 = ContextCompat.getColor(binding.root.context, R.color.Orange3)
                binding.btnRadio.setImageResource(R.drawable.radio_check_ok)//ok로 바꿈
                binding.cvOuter.setStrokeColor(orange3)




            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationChooseAdapter.ViewHolder {
        val view =
            ItemLocationChooseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationChooseAdapter.ViewHolder, position: Int) {
        holder.bind(locationlist[position],position)
    }

    override fun getItemCount(): Int {
        return locationlist.size
    }
}