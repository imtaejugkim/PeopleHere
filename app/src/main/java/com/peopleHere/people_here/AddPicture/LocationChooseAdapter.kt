package com.peopleHere.people_here.AddPicture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.Data.LocationChooseData
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ItemLocationChooseBinding

class LocationChooseAdapter(val locationlist: ArrayList<LocationChooseData>) :
    RecyclerView.Adapter<LocationChooseAdapter.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    private var selectedItemPosition = -1

    interface OnItemClickListener {
        fun onItemClick(locationlist: LocationChooseData)
    }

    inner class ViewHolder(val binding: ItemLocationChooseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(locationlist: LocationChooseData,position: Int) {
            val gray3 = ContextCompat.getColor(binding.root.context, R.color.Gray3)
            val orange3 = ContextCompat.getColor(binding.root.context, R.color.Orange3)
            /*if(selectedItemPosition!=position){
                binding.btnRadio.setImageResource(R.drawable.radio_check_no)//ok로 바꿈
                binding.cvOuter.setStrokeColor(gray3)
                notifyDataSetChanged()
            }*/
            binding.ivRegionImage.setImageResource(R.drawable.img)//여기 서버에서 받아오는 이미지로 대체
            binding.tvRegion.text = locationlist.locationName//이름
            binding.cvOuter.setOnClickListener {
                itemClickListener.onItemClick(locationlist)//하나의 객체 눌리게
                //하나만 눌리게 하려면 다른것 false로 하고  notifychanged 하면 될 듯??

/*
                if(selectedItemPosition==position){//이미 선택 되어 있으면
                    binding.btnRadio.setImageResource(R.drawable.radio_check_no)//ok로 바꿈
                    binding.cvOuter.setStrokeColor(gray3)
                }else{
                    //TODO:Location 연동되면, 최대 갯수 및 체크표시 이미지 뀌게
                    binding.btnRadio.setImageResource(R.drawable.radio_check_ok)//ok로 바꿈
                    binding.cvOuter.setStrokeColor(orange3)
                    //TODO:하고 selectedposition에 있는 아이템은 다시 색 원래로 돌리고, selectedposition update 후 notify하기
                    selectedItemPosition=position
                }
*/

                binding.btnRadio.setImageResource(R.drawable.radio_check_ok)//ok로 바꿈
                binding.cvOuter.setStrokeColor(orange3)
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationChooseAdapter.ViewHolder {
        val view =
            ItemLocationChooseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationChooseAdapter.ViewHolder, position: Int) {
        holder.bind(locationlist[position],position)
    }

    override fun getItemCount(): Int {
        return locationlist.size

    }
}