package com.example.people_here.MakingTour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.OnBoardingData
import com.example.people_here.databinding.ItemOnBoardingBinding

class OnBoardingAdapter(private val onBoardingData: List<OnBoardingData>) : RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemOnBoardingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(onBoardingInfo: OnBoardingData) {
            binding.ivOnBoardingPicture.setImageResource(onBoardingInfo.onBoardingPicture)
            binding.tvOnBoardingInfo.text = onBoardingInfo.onBoardingInfo


            if (onBoardingInfo.onBoardingNumber == 0) {
                binding.cvOnBoardingNumber.visibility = View.GONE
            } else {
                binding.tvOnBoardingNumber.text = onBoardingInfo.onBoardingNumber.toString()
                binding.cvOnBoardingNumber.visibility = View.VISIBLE
            }
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
