package com.example.people_here.MakingTour

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.MakingTourAddListData
import com.example.people_here.databinding.ItemMakingTourAddListPlace1Binding

class MakingTourAddListAdapter(val addListData : ArrayList<MakingTourAddListData>) : RecyclerView.Adapter<MakingTourAddListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMakingTourAddListPlace1Binding) : RecyclerView.ViewHolder(binding.root){
        fun bind(listInfo : MakingTourAddListData){
            binding.ivMakingTourAddListPlace.setImageResource(listInfo.placeImage)
            binding.tvMakingTourAddListPlaceName.text = listInfo.placeName
            binding.tvMakingTourListPlaceNumber.text = listInfo.placeNumber.toString()
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MakingTourAddListAdapter.ViewHolder {
       val  binding = ItemMakingTourAddListPlace1Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MakingTourAddListAdapter.ViewHolder, position: Int) {
        holder.bind(addListData[position])
    }

    override fun getItemCount(): Int = addListData.size
}