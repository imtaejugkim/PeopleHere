package com.example.people_here.MakingTour

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.MakingTourAddListData
import com.example.people_here.R
import com.example.people_here.databinding.DialogMakingTourAddListBinding
import com.example.people_here.databinding.ItemMakingTourAddListPlace1Binding
import com.example.people_here.databinding.ItemMakingTourAddListPlace2Binding
import com.example.people_here.databinding.ItemMakingTourAddListPlace3Binding
import java.util.Collections

class MakingTourAddListAdapter(private val addListData: ArrayList<MakingTourAddListData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isEditMode : Boolean = false
    private var selectedPosition: Int = RecyclerView.NO_POSITION

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

    fun removeItem(position: Int) {
        addListData.removeAt(position)
        notifyItemRemoved(position)
    }

    fun selectItem(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position

        // 이전에 선택된 아이템 업데이트
        if (previousPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(previousPosition)
        }
        // 현재 선택된 아이템 업데이트
        notifyItemChanged(selectedPosition)
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
            is FirstViewHolder -> holder.bind(item, position == selectedPosition, isEditMode)
            is SecondViewHolder -> holder.bind(item, position == selectedPosition, isEditMode)
            is LastViewHolder -> holder.bind(item)
        }
    }

    override fun getItemCount(): Int = addListData.size

    fun changeBackgroundAndPlaceNumber(context: Context, layout1: ConstraintLayout, layout2: ConstraintLayout, isSelected: Boolean) {
        val layoutBackground = if (isSelected)
            ContextCompat.getDrawable(context, R.drawable.making_tour_add_list_place_info_selected)
        else
            ContextCompat.getDrawable(context, R.drawable.making_tour_add_list_place_info)

        val placeNumberColor = if (isSelected)
            ContextCompat.getDrawable(context, R.drawable.making_tour_add_list_circle_selected)
        else
            ContextCompat.getDrawable(context, R.drawable.making_tour_add_list_circle)

        layout1.background = layoutBackground
        layout2.background = placeNumberColor
    }

    inner class FirstViewHolder(private val binding: ItemMakingTourAddListPlace1Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeInfo: MakingTourAddListData, isSelected : Boolean, isEditMode : Boolean) {
            binding.ivMakingTourAddListPlace.setImageResource(placeInfo.placeImage)
            binding.tvMakingTourAddListPlaceName.text = placeInfo.placeName
            binding.tvMakingTourListPlaceNumber.text = placeInfo.placeNumber.toString()

            changeBackgroundAndPlaceNumber(itemView.context, binding.clMakingTourPlaceInfo, binding.clMakingTourPlaceNumber, isSelected)

            itemView.setOnClickListener {
                selectItem(adapterPosition)
            }

            if (!isEditMode) {
                binding.ivMakingTourAddListPlaceDots.setImageResource(R.drawable.ic_making_tour_add_list_dots)  // dots
                binding.ivMakingTourAddListPlaceDots.setOnClickListener {
                    val dialog = ShowDialog(itemView.context, this@MakingTourAddListAdapter, adapterPosition)
                    dialog.show()
                }
            } else {
                binding.ivMakingTourAddListPlaceDots.setImageResource(R.drawable.making_tour_add_list_hamburger)  // dots
                binding.ivMakingTourAddListPlaceDots.setOnClickListener(null)
            }

        }
    }

    inner class SecondViewHolder(private val binding: ItemMakingTourAddListPlace2Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeInfo: MakingTourAddListData, isSelected: Boolean, isEditMode : Boolean) {
            binding.ivMakingTourAddListPlace.setImageResource(placeInfo.placeImage)
            binding.tvMakingTourAddListPlaceName.text = placeInfo.placeName
            binding.tvMakingTourListPlaceNumber.text = placeInfo.placeNumber.toString()

            changeBackgroundAndPlaceNumber(itemView.context, binding.clMakingTourPlaceInfo, binding.clMakingTourPlaceNumber, isSelected)

            itemView.setOnClickListener {
                selectItem(adapterPosition)
            }

            if (!isEditMode) {
                binding.ivMakingTourAddListPlaceDots.setImageResource(R.drawable.ic_making_tour_add_list_dots)  // dots
                binding.ivMakingTourAddListPlaceDots.setOnClickListener {
                    val dialog = ShowDialog(itemView.context, this@MakingTourAddListAdapter, adapterPosition)
                    dialog.show()
                }
            } else {
                binding.ivMakingTourAddListPlaceDots.setImageResource(R.drawable.making_tour_add_list_hamburger) // 햄버거 드래그
                binding.ivMakingTourAddListPlaceDots.setOnClickListener(null)
            }
        }


    }

    inner class LastViewHolder(private val binding: ItemMakingTourAddListPlace3Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeInfo: MakingTourAddListData) {
            binding.clMakingTourAddListPlacePlus.setOnClickListener {
                val dialog = ShowDialog(itemView.context, this@MakingTourAddListAdapter, adapterPosition)
                dialog.show()
            }
        }
    }

    class ShowDialog(context: Context, private val adapter: MakingTourAddListAdapter, private val position: Int) : Dialog(context) {
        lateinit var binding : DialogMakingTourAddListBinding
        init {
            binding = DialogMakingTourAddListBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnDeletePlace.setOnClickListener {
                adapter.removeItem(position)
                dismiss()
            }

            binding.btnCancel.setOnClickListener {
                dismiss()
            }
            //다이얼로그 버튼처럼 커스텁
            setDialog()
        }

        private fun setDialog() {
            val window = window
            window?.apply {
                setGravity(Gravity.BOTTOM)
                val params = attributes
                val metrics = context.resources.displayMetrics
                val width = metrics.widthPixels - 2 * dpToPx(16, context)
                params.width = width
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                attributes = params

                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }

        private fun dpToPx(dp: Int, context: Context): Int {
            return (dp * context.resources.displayMetrics.density).toInt()
        }
    }

}
