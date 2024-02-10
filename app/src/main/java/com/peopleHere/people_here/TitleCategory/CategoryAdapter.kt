package com.peopleHere.people_here.TitleCategory

import android.content.res.Resources
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.CategoryData
import com.example.people_here.R
import com.example.people_here.databinding.ItemActivityCategoryBinding

class CategoryAdapter(val categorylist: List<CategoryData>,var CheckClicked:Int) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    var CountItem: Int = 0 //3개가 되면 더 이상 누를 수 없게

    interface OnItemClickListener {
        fun onItemClick(categorylist: CategoryData)
        fun onItemNumChanged(num: Int)
    }

    //조오옹졌당
    inner class ViewHolder(val binding: ItemActivityCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(categorylist: CategoryData) {
            binding.ivRoadTour.setImageResource(categorylist.image)
            binding.tvRoadTour.text = categorylist.name
            if (!categorylist.isClicked) {
                binding.cvRoadTour.setBackgroundResource(R.drawable.category_cv_false)
                //false면 이렇게
            }
            binding.cvRoadTour.setOnClickListener {


                if (categorylist.isClicked) {
                    binding.cvRoadTour.setBackgroundResource(R.drawable.category_cv_false)
                    CountItem--
                    itemClickListener.onItemNumChanged(CountItem)
                    categorylist.isClicked = false
                } else {
                    if (CountItem < 3) {
                        binding.cvRoadTour.setBackgroundResource(R.drawable.category_cv)
                        CountItem++
                        itemClickListener.onItemNumChanged(CountItem)
                        categorylist.isClicked = true
                    } else {
                        val toastLayout = LayoutInflater.from(binding.root.context)
                            .inflate(
                                R.layout.toast_category,
                                null
                            ) // R.layout.custom_toast_layout은 사용자가 정의한 레이아웃 파일입니다.
                        val toast = Toast(binding.root.context)
                        toast.view = toastLayout
                        toast.setGravity(Gravity.BOTTOM, 0, 80.dpToPx()) // 80dp 아래로
                        toast.show()
                    }
                }


            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    fun Int.dpToPx(): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (this * scale).toInt()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.ViewHolder {
        val view =
            ItemActivityCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bind(categorylist[position])
    }

    override fun getItemCount(): Int {
        return categorylist.size
    }


}