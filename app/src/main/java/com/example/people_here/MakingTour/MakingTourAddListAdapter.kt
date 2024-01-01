package com.example.people_here.MakingTour

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.MakingTourAddListData
import com.example.people_here.R
import com.example.people_here.databinding.ItemMakingTourAddListPlace1Binding
import com.example.people_here.databinding.ItemMakingTourAddListPlace2Binding
import com.example.people_here.databinding.ItemMakingTourAddListPlace3Binding
import java.util.Collections

class MakingTourAddListAdapter(private val addListData: ArrayList<MakingTourAddListData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isEditMode : Boolean = false

    companion object {
        const val TYPE_FIRST = 0
        const val TYPE_SECOND = 1
        const val TYPE_LAST = 2
    }

    fun setEditMode(editMode: Boolean) {
        isEditMode = editMode
        notifyDataSetChanged()
    }

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(addListData, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(addListData, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
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
            else -> throw IllegalArgumentException("이외의 viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = addListData[position]
        when (holder) {
            is FirstViewHolder -> holder.bind(item, isEditMode)
            is SecondViewHolder -> holder.bind(item, isEditMode)
            is LastViewHolder -> holder.bind(item)
        }
    }

    override fun getItemCount(): Int = addListData.size

    inner class FirstViewHolder(private val binding: ItemMakingTourAddListPlace1Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeInfo: MakingTourAddListData, isEditMode : Boolean) {
            binding.ivMakingTourAddListPlace.setImageResource(placeInfo.placeImage)
            binding.tvMakingTourAddListPlaceName.text = placeInfo.placeName
            binding.tvMakingTourListPlaceNumber.text = placeInfo.placeNumber.toString()

            if (isEditMode) {
                binding.ivMakingTourAddListPlaceDots.setImageResource(R.drawable.making_tour_add_list_hamburger) // 햄버거 드래그
            } else {
                binding.ivMakingTourAddListPlaceDots.setImageResource(R.drawable.ic_making_tour_add_list_dots)  // dots
            }

        }
    }

    inner class SecondViewHolder(private val binding: ItemMakingTourAddListPlace2Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeInfo: MakingTourAddListData, isEditMode : Boolean) {
            binding.ivMakingTourAddListPlace.setImageResource(placeInfo.placeImage)
            binding.tvMakingTourAddListPlaceName.text = placeInfo.placeName
            binding.tvMakingTourListPlaceNumber.text = placeInfo.placeNumber.toString()

            if (isEditMode) {
                binding.ivMakingTourAddListPlaceDots.setImageResource(R.drawable.making_tour_add_list_hamburger) // 햄버거 드래그
            } else {
                binding.ivMakingTourAddListPlaceDots.setImageResource(R.drawable.ic_making_tour_add_list_dots)  // dots
            }
        }


    }

    inner class LastViewHolder(private val binding: ItemMakingTourAddListPlace3Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeInfo: MakingTourAddListData) {
            binding.clMakingTourAddListPlacePlus.setOnClickListener {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        TODO("Not yet implemented")
    }
}
