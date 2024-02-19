package com.peopleHere.people_here.Main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.Data.MainSearchData
import com.peopleHere.people_here.Data.MakingCourseSearchData
import com.peopleHere.people_here.Remote.RecentSearchResponse
import com.peopleHere.people_here.databinding.ItemMainSearchBinding

class MainSearchRecentAdapter(var mainSearchData : ArrayList<RecentSearchResponse>,
                              private val listener: OnPlaceRecentClickListener
) : RecyclerView.Adapter<MainSearchRecentAdapter.ViewHolder>() {

    interface OnPlaceRecentClickListener {
        fun onPlaceClick(data: RecentSearchResponse)
    }

    inner class ViewHolder(val binding : ItemMainSearchBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(searchRecentInfo : RecentSearchResponse) {
            binding.tvMainSearchRecentRegion.text = searchRecentInfo.placeName
            binding.tvMainSearchRecentPlace.text = searchRecentInfo.placeAddress
            binding.clAddList.setOnClickListener {
                listener.onPlaceClick(searchRecentInfo)
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainSearchRecentAdapter.ViewHolder {
        val binding = ItemMainSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainSearchRecentAdapter.ViewHolder, position: Int) {
        holder.bind(mainSearchData[position])
    }

    override fun getItemCount(): Int = mainSearchData.size

    fun updateData(newData: ArrayList<RecentSearchResponse>) {
        mainSearchData = newData
        notifyDataSetChanged()
    }
}