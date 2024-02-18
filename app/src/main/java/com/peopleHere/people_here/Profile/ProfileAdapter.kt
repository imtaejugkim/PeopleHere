package com.peopleHere.people_here.Profile

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peopleHere.people_here.AddPicture.PictureDB.PictureEntity
import com.peopleHere.people_here.Data.CurrentUserData
import com.peopleHere.people_here.Data.ProfileData
import com.peopleHere.people_here.Data.UpcomingTours
import com.peopleHere.people_here.databinding.ItemAddPictureBinding
import com.peopleHere.people_here.databinding.ItemProfilePastMeetBinding
import com.peopleHere.people_here.databinding.ItemProfileProductBinding

class ProfileAdapter(var profiledata: ProfileData, context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_FIRST = 0//다가
        const val TYPE_SECOND = 1//지난
        const val TYPE_THIRD = 2//no
    }

    /*
        override fun getItemViewType(position: Int): Int {//entity의 순서
            //TODO:추후 , upcoming인지 뭔지를 통해 itemType 분류하기
            return profiledata[position]
     }*/


    interface OnItemClickListener {
        fun onItemClick(size: Int)
    }

    override fun getItemCount(): Int {
        return profiledata.upcomingTours.size

    }

    override fun onCreateViewHolder(//이거 다시
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_FIRST -> {
                val binding = ItemProfileProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FirstViewHolder(binding)
            }
            TYPE_SECOND -> {
                val binding = ItemProfilePastMeetBinding.inflate(
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
        val item1 = profiledata.upcomingTours[position]
        val item2 = profiledata.pastTours[position]


        when (holder) {
            is ProfileAdapter.FirstViewHolder -> holder.bind(item1)//upcoming
            is ProfileAdapter.SecondViewHolder -> holder.bind(item2)
        }
    }

    inner class FirstViewHolder(private val binding: ItemProfileProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(upcomingTours: UpcomingTours) {
            Glide.with(binding.root)
                .load(Uri.parse(upcomingTours.oppositeUserInfo.userImageUrl)) // 이미지 경로를 로드
                .into(binding.ivProfileImg) // ImageView에 이미지 표시
            //TODO:얼마 남았는지?
            binding.tvTourName.text = upcomingTours.tourName//이름 설정
            binding.tvDate.text = upcomingTours.tourDateTime
            binding.tvUserName.text = upcomingTours.oppositeUserInfo.userName
            binding.tvPlace.text = upcomingTours.firstPlaceInfo.placeName
            binding.tvPlaceSmall.text = upcomingTours.firstPlaceInfo.placeAddress
        }
    }

    inner class SecondViewHolder(private val binding: ItemProfilePastMeetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(past: UpcomingTours) {
            Glide.with(binding.root)
                .load(Uri.parse(past.oppositeUserInfo.userImageUrl)) // 이미지 경로를 로드
                .into(binding.ivProfileImg)
            binding.tvItemPastMeetTitle.text = past.tourName
            binding.tvDate.text = past.tourDateTime
            binding.tvItemPastMeetName.text = past.oppositeUserInfo.userName
        }


    }


}