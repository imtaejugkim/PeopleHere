package com.peopleHere.people_here.Profile

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peopleHere.people_here.Data.ProfileData
import com.peopleHere.people_here.Data.UpcomingTours
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ItemLocationChooseBinding
import com.peopleHere.people_here.databinding.ItemProfilePastMeetBinding
import com.peopleHere.people_here.databinding.ItemProfileProductBinding

class ProfilePastAdapter(var profiledata: ProfileData, context: Context) :
    RecyclerView.Adapter<ProfilePastAdapter.ViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(size: Int)
    }

    override fun getItemCount(): Int {
        return profiledata.pastTours.size
    }

    override fun onCreateViewHolder(//이거 다시
        parent: ViewGroup,
        viewType: Int
    ): ProfilePastAdapter.ViewHolder {

        val view =
            ItemProfilePastMeetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ProfilePastAdapter.ViewHolder, position: Int) {
        holder.bind(profiledata.pastTours[position])
    }


    inner class ViewHolder(private val binding: ItemProfilePastMeetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(past: UpcomingTours) {
            past.oppositeUserInfo?.userImageUrl?.let { imageUrl ->
                Glide.with(binding.root)
                    .load(Uri.parse(imageUrl))
                    .into(binding.ivProfileImg) // ImageView에 이미지를 표시합니다.
            } ?: run {
                Glide.with(binding.root)
                    .load(R.drawable.profile_olivia)
                    .into(binding.ivProfileImg)
            }
            binding.tvItemPastMeetTitle.text = past.tourName


            val year = past .tourDateTime?.substring(0, 4)
            val month=past.tourDateTime?.substring(5, 7)
            val day=past.tourDateTime?.substring(8, 10)
            val time=past.tourDateTime?.substring(11, 13)?.toInt()
            var insertTime=""
            if(time!! <13){
                insertTime="오전 ${time}시"
            }else{
                insertTime="오후 ${time}시"
            }



            //0~10 받음
            binding.tvDate.text = "${year}년 ${month}월 ${day}일 ${insertTime}"


            binding.tvItemPastMeetName.text = past.oppositeUserInfo?.userName

        }


    }


}