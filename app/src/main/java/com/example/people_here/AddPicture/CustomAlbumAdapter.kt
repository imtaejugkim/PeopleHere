package com.example.people_here.AddPicture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.CustomAlbumData
import com.example.people_here.Data.LocationChooseData
import com.example.people_here.R
import com.example.people_here.databinding.ItemCustoAlbumBinding
import com.example.people_here.databinding.ItemLocationChooseBinding

class CustomAlbumAdapter(val picturelist: ArrayList<CustomAlbumData>) :
    RecyclerView.Adapter<CustomAlbumAdapter.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(picturelist: CustomAlbumData)
    }

    inner class ViewHolder(val binding: ItemCustoAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(picturelist: CustomAlbumData) {
            //여기 각각 갤러리에서 메타 데이터 받아와서 넣기

            binding.ivProfile.setImageResource(R.drawable.img)
            binding.ivProfile.setOnClickListener {
                itemClickListener.onItemClick(picturelist)//하나의 객체 눌리게

            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomAlbumAdapter.ViewHolder {
        val view =
            ItemCustoAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomAlbumAdapter.ViewHolder, position: Int) {
        holder.bind(picturelist[position])
    }

    override fun getItemCount(): Int {
        return picturelist.size
    }
}