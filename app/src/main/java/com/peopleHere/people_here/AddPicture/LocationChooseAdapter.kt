package com.peopleHere.people_here.AddPicture

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.Data.LocationChooseData
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ItemLocationChooseBinding

class LocationChooseAdapter(val locationlist: ArrayList<LocationChooseData>) :
    RecyclerView.Adapter<LocationChooseAdapter.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    private var selectedItemPosition: Int = 0
    var checkSelected:Boolean=false

    interface OnItemClickListener {
        fun onItemClick(locationlist: LocationChooseData,checkSelected:Boolean)
    }

    inner class ViewHolder(val binding: ItemLocationChooseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(locationlist: LocationChooseData, position: Int) {
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

                //하나만 눌리게 하려면 다른것 false로 하고  notifychanged 하면 될 듯??

                //만약 다시 눌린 경우->이게 안되네 흠
                //일단 다 false라 눌리면 true 되어야 하는데 왜 이렇게 되는갸

                if (locationlist.selected) {//true->false
                    locationlist.selected = false
                    checkSelected=false
                    Log.d("soccer", locationlist.selected.toString())
                    binding.btnRadio.setImageResource(R.drawable.radio_check_no)//ok로 바꿈
                    binding.cvOuter.setStrokeColor(gray3)

                } else {//false인게 눌린경우
                    //TODO:Location 연동되면, 최대 갯수 및 체크표시 이미지 뀌게
                    //TODO:하고 selectedposition에 있는 아이템은 다시 색 원래로 돌리고, selectedposition update 후 notify하기
                    //이전꺼 false 이번꺼 true 되게
                    ChangeSelected()
                    locationlist.selected = true
                    checkSelected=true
                    selectedItemPosition = position
                    //position 업데이트
                }
                Log.d("isCliecked_ada",checkSelected.toString())

                itemClickListener.onItemClick(locationlist,checkSelected)//하나의 객체 눌리게

            }//왜 같은 것을 눌렀을 때 흠 이건 어케 해결하지

            if (locationlist.selected) {
                Log.d("soccer_ok", "hi")
                binding.btnRadio.setImageResource(R.drawable.radio_check_ok)//ok로 바꿈
                binding.cvOuter.setStrokeColor(orange3)
            } else {
                Log.d("soccer_no", "hi")
                binding.btnRadio.setImageResource(R.drawable.radio_check_no)//ok로 바꿈
                binding.cvOuter.setStrokeColor(gray3)
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
        holder.bind(locationlist[position], position)
    }

    override fun getItemCount(): Int {
        return locationlist.size
    }

    fun ChangeSelected() {
        locationlist[selectedItemPosition].selected = false
        notifyDataSetChanged()
    }
}