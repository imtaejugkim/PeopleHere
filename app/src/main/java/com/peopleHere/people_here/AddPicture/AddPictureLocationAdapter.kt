package com.peopleHere.people_here.AddPicture

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.AddPicture.PictureDB.PictureDB
import com.peopleHere.people_here.Data.AddPicturLocationData
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ItemAddpictureLocationNameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddPictureLocationAdapter(
    var addlocationlist: ArrayList<AddPicturLocationData>,
    val context: Context, var locationName: String
) :
    RecyclerView.Adapter<AddPictureLocationAdapter.ViewHolder>() {
    var pictureDB = PictureDB.getInstance(context, false) //인스턴스 생성
    private lateinit var itemClickListener: AddPictureLocationAdapter.OnItemClickListener

    interface OnItemClickListener {
        fun onPictureNum(size: Int)
    }

    fun setOnItemClickListener(onItemClickListener: AddPictureLocationAdapter.OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int {
        return addlocationlist.size
    }

    override fun onCreateViewHolder(//이거 다시
        parent: ViewGroup,
        viewType: Int
    ): AddPictureLocationAdapter.ViewHolder {

        val view =
            ItemAddpictureLocationNameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddPictureLocationAdapter.ViewHolder, position: Int) {
        holder.bind(addlocationlist[position])
    }

    inner class ViewHolder(private val binding: ItemAddpictureLocationNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(addlocation: AddPicturLocationData) {//여기의 locationName값은 다 같잖아??
            val orange5 = ContextCompat.getColor(binding.root.context, R.color.Orange5)
            val gray5 = ContextCompat.getColor(binding.root.context, R.color.Gray5)

            //TODO:왜 둘 중 하나만 색이 생기나용???
            if (addlocation.pictureNum > 0) {
                binding.tvLocation.setTextColor(orange5)
                binding.ivCheck.setBackgroundResource(R.drawable.checked)
            } else {
                binding.tvLocation.setTextColor(gray5)
                binding.ivCheck.setBackgroundResource(R.drawable.checked_no)
            }


            val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    // DB에서 개수 가져와서 0개 밑이면 체크 풀리게 하고 이상이면 체크 있게 하면 되는거 아니고?
                    //locationName 가져와서 none이 아니면 , 가져오게 해서 데이터 값 넣기
                    val orange5 = ContextCompat.getColor(binding.root.context, R.color.Orange5)
                    val gray5 = ContextCompat.getColor(binding.root.context, R.color.Gray5)
                    if (locationName == addlocation.location) {
                        addlocation.pictureNum =
                            pictureDB!!.getPictureDao().getPictureNum(locationName)//개수 가져와서 넣기
                    }

                    if (addlocation.pictureNum > 0) {
                        binding.tvLocation.setTextColor(orange5)
                        binding.ivCheck.setBackgroundResource(R.drawable.checked)
                    } else {
                        binding.tvLocation.setTextColor(gray5)
                        binding.ivCheck.setBackgroundResource(R.drawable.checked_no)

                    }
                }
            }

            binding.tvLocation.text = addlocation.location

        }


    }


}
