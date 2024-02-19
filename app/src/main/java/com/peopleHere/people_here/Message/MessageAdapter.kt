package com.peopleHere.people_here.Message

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peopleHere.people_here.Data.ChatData
import com.peopleHere.people_here.Data.ProfileData
import com.peopleHere.people_here.Data.UpcomingTours
import com.peopleHere.people_here.Profile.ProfilePastAdapter
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ItemMessageBinding
import com.peopleHere.people_here.databinding.ItemProfilePastMeetBinding
import java.time.Duration
import java.time.LocalDateTime

class MessageAdapter(var chatData: ArrayList<ChatData>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(size: Int)
    }

    override fun getItemCount(): Int {
        return chatData.size
    }

    override fun onCreateViewHolder(//이거 다시
        parent: ViewGroup,
        viewType: Int
    ): MessageAdapter.ViewHolder {

        val view =
            ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        holder.bind(chatData[position])
    }

    inner class ViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatData: ChatData) {


            chatData.participants?.userImageUrl?.let { imageUrl ->
                Glide.with(binding.root)
                    .load(Uri.parse(imageUrl))
                    .into(binding.ivProfileImg) // ImageView에 이미지를 표시합니다.
            } ?: run {
                Glide.with(binding.root)
                    .load(R.drawable.profile_olivia)
                    .into(binding.ivProfileImg)
            }

            val year = chatData.date?.substring(0, 4)?.toInt()//2024
            val month=chatData.date?.substring(5, 7)?.toInt()//02
            val day=chatData.date?.substring(8, 10)?.toInt()//19
            val time=chatData.time?.hour
            val minute=chatData.time?.minute

            var insertTime=""
            Log.d("datacheck_year",year.toString())
            Log.d("datacheck_monthr",month.toString())
            Log.d("datacheck_day",day.toString())
            Log.d("datacheck_time",time.toString())
            Log.d("datacheck_minute",minute.toString())

            if (time != null) {
                insertTime = if(time <13){
                    "오전 ${time}시"
                }else{
                    "오후 ${time}시"
                }
                val now = LocalDateTime.now()
                val givenTime = LocalDateTime.of(year!!, month!!, day!!, time!!, 0) // 예시 시간: 2024년 2월 19일 오전 12시
                val duration = Duration.between(now, givenTime)
                val days = duration.toDays()
                if(days>=1){
                    binding.tvDate.text="${year}년 ${month}월 ${day}일 (요일) 시간 협의"
                    binding.tvText.text="확정 대기중 ㅇㄹ낭라ㅣ"
                }else if(days.toInt() ==0){
                    binding.tvDate.text="오늘 ${insertTime}:${minute}"
                }

            }



            binding.tvName.text=chatData.participants.userName
            if(chatData.status=="AVAILABLE"){
                binding.tvUpcoming.text="다가오는 만남"
            }

/*
            //TODO:계산해서 1일 내면 오전 어쩌구로 아니면 그냥 년월
            val year = chatData.date?.substring(0, 4)
            val month = chatData.date?.substring(5, 7)
            val day = chatData.date?.substring(8, 10)
            val time = chatData.date?.substring(11, 13)?.toInt()
            var insertTime = ""
            if (time!! < 13) {
                insertTime = "오전 ${time}시"
            } else {
                insertTime = "오후 ${time}시"
            }
*/
        }


    }


}