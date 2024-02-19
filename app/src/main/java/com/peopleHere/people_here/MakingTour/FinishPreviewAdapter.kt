package com.peopleHere.people_here.MakingTour

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.Data.MainCourseData
import com.peopleHere.people_here.databinding.ItemMainTourListCourse2Binding
import com.bumptech.glide.Glide
import com.peopleHere.people_here.R

class FinishPreviewAdapter(private val mainCourseData: ArrayList<MainCourseData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMainTourListCourse2Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tourListInfo2 : MainCourseData) {
            setImage(binding.root.context, binding.ivMainTourListUserCourse, tourListInfo2)
            binding.tvItemMainTourListRegion.text = tourListInfo2.placeName
        }
    }

    private fun setImage(context: Context, imageView: ImageView, tourListInfo: MainCourseData) {
        if(tourListInfo.placeImages[0] == "string") {
            imageView.setImageResource(R.drawable.img_example_contents)
        } else {
            Glide.with(context)
                .load(tourListInfo.placeImages[0])
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishPreviewAdapter.ViewHolder {
        val binding = ItemMainTourListCourse2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = mainCourseData.size
}
