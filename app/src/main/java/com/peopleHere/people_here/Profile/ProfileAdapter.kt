package com.peopleHere.people_here.Profile

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.type.DateTime
import com.peopleHere.people_here.AddPicture.PictureDB.PictureEntity
import com.peopleHere.people_here.Data.CurrentUserData
import com.peopleHere.people_here.Data.ProfileData
import com.peopleHere.people_here.Data.UpcomingTours
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ItemAddPictureBinding
import com.peopleHere.people_here.databinding.ItemProfilePastMeetBinding
import com.peopleHere.people_here.databinding.ItemProfileProductBinding
import java.time.Duration
import java.time.LocalDateTime

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

            upcomingTours.oppositeUserInfo?.userImageUrl?.let { imageUrl ->
                Glide.with(binding.root)
                    .load(Uri.parse(imageUrl))
                    .into(binding.ivProfileImg) // ImageView에 이미지를 표시합니다.
            } ?: run {
                Glide.with(binding.root)
                    .load(R.drawable.profile_olivia)
                    .into(binding.ivProfileImg)
            }
            //TODO:얼마 남았는지?

            binding.tvTourName.text = upcomingTours.tourName//이름 설정
            val year = upcomingTours.tourDateTime?.substring(0, 4)?.toInt()
            val month=upcomingTours.tourDateTime?.substring(5, 7)?.toInt()
            val day=upcomingTours.tourDateTime?.substring(8, 10)?.toInt()
            val time=upcomingTours.tourDateTime?.substring(11, 13)?.toInt()
            var insertTime=""
            if(time!! <13){
                insertTime="오전 ${time}시"
            }else{
                insertTime="오후 ${time}시"
            }


            val now = LocalDateTime.now()
            // 주어진 시
            val givenTime = LocalDateTime.of(year!!, month!!, day!!, time, 0) // 예시 시간: 2024년 2월 19일 오전 12시
            // 현재 시간과 주어진 시간의 차이 계산

            val duration = Duration.between(now, givenTime)

            // 차이를 일 단위로 변환하여 출력
            val days = duration.toDays()
            Log.d("dayscheck",days.toString())
            val orange1 = ContextCompat.getColor(binding.root.context, R.color.Orange1)

            val orange5 = ContextCompat.getColor(binding.root.context, R.color.Orange5)
            when (days) {
                0.toLong() -> {
                    binding.tvWhen.text="오늘 만나요"
                }
                in 1..6 -> {
                    binding.tvWhen.text="${days}일 후에  만나요"
                    binding.tvWhen.setTextColor(orange5)
                    binding.cvWhen.setBackgroundResource(R.drawable.profile_when)
                }
                in 7..30 -> {
                    var weeks=days/7
                    binding.tvWhen.text="${weeks}주 후에  만나요"
                    binding.tvWhen.setTextColor(orange5)

                    binding.cvWhen.setBackgroundResource(R.drawable.profile_when)
                }
                else->{
                    var months=days/30
                    binding.tvWhen.text="${months}개월 후에  만나요"
                    binding.tvWhen.setTextColor(orange5)

                    binding.cvWhen.setBackgroundResource(R.drawable.profile_when)
                }

            }





            binding.tvDate.text = "${year}년 ${month}월 ${day}일 ${insertTime}"

            binding.tvUserName.text = upcomingTours.oppositeUserInfo?.userName
            binding.tvPlace.text = upcomingTours.firstPlaceInfo.placeName
            binding.tvPlaceSmall.text = upcomingTours.firstPlaceInfo.placeAddress
        }
    }

    inner class SecondViewHolder(private val binding: ItemProfilePastMeetBinding) :
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
            binding.tvDate.text = past.tourDateTime
            binding.tvItemPastMeetName.text = past.oppositeUserInfo?.userName

        }


    }
    fun setData(products: ProfileData) {
        profiledata= products as ProfileData
        notifyDataSetChanged()
        //TO DO:HomeFragment 에서 생성해서 data setting 해주는 역할
        //아 마찬가지로 이거쓰면 ㄱㅊ할듯한데그냥 list안쓰고 흠흠
    }


}