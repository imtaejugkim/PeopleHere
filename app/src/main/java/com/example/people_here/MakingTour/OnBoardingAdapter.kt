package com.example.people_here.MakingTour

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.OnBoardingData
import com.example.people_here.databinding.ItemOnBoardingBinding

class OnBoardingAdapter(val onBoardingData : List<OnBoardingData>) : RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {
    inner class ViewHolder(val binding : ItemOnBoardingBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(onBoardingInfo : OnBoardingData){
            binding.ivOnBoardingPicture.setImageResource(onBoardingInfo.onBoardingPicture)
            binding.tvOnBoardingNumber.text = onBoardingInfo.onBoardingNumber.toString()
            binding.tvOnBoardingInfo.text = onBoardingInfo.onBoardingInfo

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOnBoardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = onBoardingData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(onBoardingData[position])
    }
}