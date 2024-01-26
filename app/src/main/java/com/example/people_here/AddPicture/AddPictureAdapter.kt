package com.example.people_here.AddPicture

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.people_here.Data.AddPictureData
import com.example.people_here.Data.MakingTourAddListData
import com.example.people_here.MakingTour.MakingTourAddListAdapter
import com.example.people_here.R
import com.example.people_here.databinding.ItemAddPictureBinding
import com.example.people_here.databinding.ItemAddPictureFirstBinding
import com.example.people_here.databinding.ItemMakingTourAddListPlace1Binding
import com.example.people_here.databinding.ItemMakingTourAddListPlace2Binding
import com.example.people_here.databinding.ItemMakingTourAddListPlace3Binding


class AddPictureAdapter(val picturelist: ArrayList<AddPictureData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    //사진 추가& 더 이상 추가 못 하는 버튼을 type1
    //나머지를 type second
    companion object {
        const val TYPE_FIRST = 0
        const val TYPE_SECOND = 1
    }


    override fun getItemViewType(position: Int): Int {
        return picturelist[position].itemType
    }


    interface OnItemClickListener {
        fun onItemClick(picturelist: AddPictureData)
    }

    inner class FirstViewHolder(private val binding: ItemAddPictureFirstBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(picturelist: AddPictureData) {

            Glide.with(binding.root)
                .load(Uri.parse(picturelist.imageUrl)) // 이미지 경로를 로드
                .into(binding.ivAddPicture) // ImageView에 이미지 표시

            Log.d("qwer_addadt",picturelist.imageUrl)
            binding.ivAddPicture.setOnClickListener {
                //눌리면 사진 추가 기능 하게
                val bottomsheet = LocationChooseFragment()
                val activityContext = binding.root.context
                val fragmentManager = (activityContext as AppCompatActivity).supportFragmentManager
                bottomsheet.show(fragmentManager, bottomsheet.tag)
            }
        }
    }

    inner class SecondViewHolder(private val binding: ItemAddPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(picturelist: AddPictureData,position: Int) {
            Glide.with(binding.root)
                .load(Uri.parse(picturelist.imageUrl)) // URI 문자열을 URI로 변환
                .into(binding.ivPicture)
            //x버튼 눌르면 이미지 사라지게
            binding.ivBtn.setOnClickListener{
                removeItem(position)
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
           TYPE_FIRST -> {
                val binding = ItemAddPictureFirstBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FirstViewHolder(binding)
            }
            TYPE_SECOND -> {
                val binding = ItemAddPictureBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                SecondViewHolder(binding)
            }
            else -> throw IllegalArgumentException("이외의 viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = picturelist[position]
        when (holder) {
            is AddPictureAdapter.FirstViewHolder -> holder.bind(item)
            is AddPictureAdapter.SecondViewHolder -> holder.bind(item,position)
        }
    }

    override fun getItemCount(): Int {
        return picturelist.size
    }

    fun removeItem(position: Int) {
        picturelist.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, picturelist.size)
    }

}
