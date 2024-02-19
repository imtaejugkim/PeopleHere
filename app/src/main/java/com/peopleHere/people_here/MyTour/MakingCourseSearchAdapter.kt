package com.peopleHere.people_here.Main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.Data.MainData
import com.peopleHere.people_here.Data.MainSearchData
import com.peopleHere.people_here.Data.MakingCourseSearchData
import com.peopleHere.people_here.databinding.ItemMainSearchBinding

class MakingCourseSearchAdapter(var makingSearchData : ArrayList<MakingCourseSearchData>,
                                private val listener: OnPlaceClickListener  // 리스너 추가
) : RecyclerView.Adapter<MakingCourseSearchAdapter.ViewHolder>() {
    private lateinit var placeClickListener: OnPlaceClickListener

    interface OnPlaceClickListener {
        fun onPlaceClick(data: MakingCourseSearchData)
    }

    inner class ViewHolder(val binding : ItemMainSearchBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(searchInfo : MakingCourseSearchData) {
            binding.tvMainSearchRecentRegion.text = searchInfo.searchRegion
            binding.tvMainSearchRecentPlace.text = searchInfo.searchPlace
            binding.clAddList.setOnClickListener {
                listener.onPlaceClick(searchInfo)
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MakingCourseSearchAdapter.ViewHolder {
        val binding = ItemMainSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MakingCourseSearchAdapter.ViewHolder, position: Int) {
        holder.bind(makingSearchData[position])
    }

    override fun getItemCount(): Int = makingSearchData.size

    fun updateData(newData: ArrayList<MakingCourseSearchData>) {
        makingSearchData = newData
        notifyDataSetChanged()
    }

    fun setOnPlaceListener(onPlaceClickListener : OnPlaceClickListener){
        placeClickListener = onPlaceClickListener
    }
}