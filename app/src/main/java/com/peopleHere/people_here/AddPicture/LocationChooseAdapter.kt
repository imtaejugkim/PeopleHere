package com.peopleHere.people_here.AddPicture

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peopleHere.people_here.AddPicture.PictureDB.PictureDB
import com.peopleHere.people_here.Data.LocationChooseData
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ItemLocationChooseBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationChooseAdapter(val locationlist: ArrayList<LocationChooseData>, val context: Context) :
    RecyclerView.Adapter<LocationChooseAdapter.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    var pictureDB = PictureDB.getInstance(context,false) //인스턴스 생성

    private var selectedItemPosition: Int = 0
    var checkSelected: Boolean = false

    var fordb: Boolean = true

    interface OnItemClickListener {
        fun onItemClick(locationlist: LocationChooseData, checkSelected: Boolean)
        fun clickLocation(locationName: String, picturNum: Int)
    }

    inner class ViewHolder(val binding: ItemLocationChooseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun Dbadd() {
            if (fordb) {
                fordb = false
                val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        for (i in 0 until locationlist.size) {
                            locationlist[i].picutrNum =
                                pictureDB!!.getPictureDao().getPictureNum(locationlist[i].locationName)//개수 가져와서 넣기
                            itemClickListener.clickLocation(locationlist[i].locationName,locationlist[i].picutrNum)//개수 넘기기
                        }
                    }
                }
            } else {
                return
            }


        }

        fun bind(locationlist: LocationChooseData, position: Int) {
            val gray3 = ContextCompat.getColor(binding.root.context, R.color.Gray3)
            val orange3 = ContextCompat.getColor(binding.root.context, R.color.Orange3)
            if(locationlist.picutrNum>0){
                binding.ivCheck.setImageResource(R.drawable.checked)
            }else{
                binding.ivCheck.setImageResource(0)
            }
            Dbadd()
            //TODO:왜 클릭 됐을떄죠 비동기인건 알겠는데...?


            Glide.with(context)
                .load(locationlist.locationImage)
                .into(binding.ivRegionImage)

            binding.tvRegion.text = locationlist.locationName//이름

            binding.cvOuter.setOnClickListener {
                //TODO:이상한게 건대 눌러도 왜 롯데마트가 가지지??
                itemClickListener.clickLocation(locationlist.locationName, locationlist.picutrNum)

                if (locationlist.selected) {//true->false
                    locationlist.selected = false
                    checkSelected = false
                    Log.d("soccer", locationlist.selected.toString())
                    binding.btnRadio.setImageResource(R.drawable.radio_check_no)//ok로 바꿈
                    binding.cvOuter.setStrokeColor(gray3)

                } else {//false인게 눌린경우
                    ChangeSelected()
                    locationlist.selected = true
                    checkSelected = true
                    selectedItemPosition = position
                    //position 업데이트
                }
                itemClickListener.onItemClick(locationlist, checkSelected)//하나의 객체 눌리게

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