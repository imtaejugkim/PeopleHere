package com.example.people_here.MakingTour

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.MakingTourAddListData
import com.example.people_here.databinding.ItemMakingTourAddListPlace1Binding
import com.example.people_here.databinding.ItemMakingTourAddListPlace2Binding
import com.example.people_here.databinding.ItemMakingTourAddListPlace3Binding

class MakingTourAddListAdapter(private val addListData: ArrayList<MakingTourAddListData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_FIRST = 0
        const val TYPE_SECOND = 1
        const val TYPE_LAST = 2
    }

    override fun getItemViewType(position: Int): Int {
        return addListData[position].itemType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_FIRST -> {
                val binding = ItemMakingTourAddListPlace1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                FirstViewHolder(binding)
            }
            TYPE_SECOND -> {
                val binding = ItemMakingTourAddListPlace2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                SecondViewHolder(binding)
            }
            TYPE_LAST -> {
                val binding = ItemMakingTourAddListPlace3Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                LastViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = addListData[position]
        when (holder) {
            is FirstViewHolder -> holder.bind(item)
            is SecondViewHolder -> holder.bind(item)
            is LastViewHolder -> holder.bind(item)
        }
    }

    override fun getItemCount(): Int = addListData.size

    inner class FirstViewHolder(private val binding: ItemMakingTourAddListPlace1Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeInfo: MakingTourAddListData) {
            binding.ivMakingTourAddListPlace.setImageResource(placeInfo.placeImage)
            binding.tvMakingTourAddListPlaceName.text = placeInfo.placeName
            binding.tvMakingTourListPlaceNumber.text = placeInfo.placeNumber.toString()
        }
    }

    inner class SecondViewHolder(private val binding: ItemMakingTourAddListPlace2Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeInfo: MakingTourAddListData) {
            binding.ivMakingTourAddListPlace.setImageResource(placeInfo.placeImage)
            binding.tvMakingTourAddListPlaceName.text = placeInfo.placeName
            binding.tvMakingTourListPlaceNumber.text = placeInfo.placeNumber.toString()
        }
    }

    inner class LastViewHolder(private val binding: ItemMakingTourAddListPlace3Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeInfo: MakingTourAddListData) {
            // '장소 추가' 아이템 바인딩 로직
            binding.clMakingTourAddListPlacePlus.setOnClickListener {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        TODO("Not yet implemented")
    }
}
