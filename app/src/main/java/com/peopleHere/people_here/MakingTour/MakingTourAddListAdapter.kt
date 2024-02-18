package com.peopleHere.people_here.MakingTour

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peopleHere.people_here.Data.MakingTourAddListData
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.DialogMakingTourAddListBinding
import com.peopleHere.people_here.databinding.ItemMakingTourAddListPlace1Binding
import java.util.Collections

class MakingTourAddListAdapter(private val addListData: ArrayList<MakingTourAddListData>,
                               val context: Context,
                               private val itemCountChangedListener: OnItemCountChangedListener? = null
) : RecyclerView.Adapter<MakingTourAddListAdapter.ViewHolder>() {

    private var isEditMode: Boolean = false
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    interface OnItemCountChangedListener {
        fun onItemCountChanged(count: Int)
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
        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedPosition)
    }

    inner class ViewHolder(private val binding: ItemMakingTourAddListPlace1Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeInfo: MakingTourAddListData, isSelected: Boolean, isEditMode: Boolean) {
            Glide.with(context)
                .load(placeInfo.placeImage)
                .into(binding.ivMakingTourAddListPlace)

            binding.tvMakingTourAddListPlaceName.text = placeInfo.placeName
            binding.tvMakingTourListPlaceNumber.text = (adapterPosition+1).toString()

            when (adapterPosition) {
                0 -> {
                    binding.tvMakingTourAddListPlaceFirst.visibility = View.VISIBLE
                }
                else -> {
                    binding.tvMakingTourAddListPlaceFirst.visibility = View.GONE
                }
            }

            itemView.setOnClickListener {
                selectItem(adapterPosition)
            }

            val iconRes = if (isEditMode) R.drawable.making_tour_add_list_hamburger else R.drawable.ic_trash
            binding.ivMakingTourAddListPlaceDots.setImageResource(iconRes)
            if (!isEditMode) {
                binding.ivMakingTourAddListPlaceDots.setOnClickListener {
                    val dialog = ShowDialog(itemView.context, this@MakingTourAddListAdapter, adapterPosition)
                    dialog.show()
                }
            } else {
                binding.ivMakingTourAddListPlaceDots.setOnClickListener(null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMakingTourAddListPlace1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(addListData[position], position == selectedPosition, isEditMode)
    }

    override fun getItemCount(): Int = addListData.size

    class ShowDialog(context: Context, private val adapter: MakingTourAddListAdapter, private val position: Int) : Dialog(context) {
        lateinit var binding : DialogMakingTourAddListBinding
        init {
            binding = DialogMakingTourAddListBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnDeletePlace.setOnClickListener {
                if (context is MakingTourAddListActivity) {
                    context.removeItemAndMarker(position)
                }
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
