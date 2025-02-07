package com.peopleHere.people_here.AddPicture

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.play.integrity.internal.i
import com.peopleHere.people_here.AddPicture.PictureDB.PictureDB
import com.peopleHere.people_here.AddPicture.PictureDB.PictureEntity
import com.peopleHere.people_here.Data.AddPictureData
import com.peopleHere.people_here.databinding.ItemAddPictureBinding
import com.peopleHere.people_here.databinding.ItemAddPictureFirstBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections


class AddPictureAdapter(var picturelist: ArrayList<PictureEntity>, context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    var pictureDB = PictureDB.getInstance(context,false) //인스턴스 생성


    //사진 추가& 더 이상 추가 못 하는 버튼을 type1
    //나머지를 type second
    companion object {
        const val TYPE_FIRST = 0
        const val TYPE_SECOND = 1
    }


    override fun getItemViewType(position: Int): Int {//entity의 순서
        return picturelist[position].itemType
    }


    interface OnItemClickListener {

        fun onItemClick(size: Int)
    }

    inner class FirstViewHolder(private val binding: ItemAddPictureFirstBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(picturelist: PictureEntity) {
            Glide.with(binding.root)
                .load(Uri.parse(picturelist.pictureUri)) // 이미지 경로를 로드
                .into(binding.ivAddPicture) // ImageView에 이미지 표시

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

        private var getPosition: Int? = null
        var num=0
        fun bind(picturelist: PictureEntity, position: Int) {
            Glide.with(binding.root)
                .load(Uri.parse(picturelist.pictureUri)) // URI 문자열을 URI로 변환
                .into(binding.ivPicture)
            //x버튼 눌르면 이미지 사라지게 //인스턴스 생성
            //TODO: 장소이름 넣으면 될듯??
            if (position == 0) {
                binding.cvString.visibility = View.VISIBLE
            } else {
                binding.cvString.visibility = View.INVISIBLE
            } //벗어나면 다시 없애야함
            val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    binding.tvLocationName.text = picturelist.loactionName
                }
            }
            binding.ivBtn.setOnClickListener {
                removeItem(position)

                val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        // 비동기로 데이터베이스에서 삭제

                        pictureDB!!.getPictureDao().deletePicture(picturelist.pictureUri)
                        itemClickListener.onItemClick(pictureDB.getPictureDao().getPicture().size)
                        Log.d("qwer_deleteCheck", "1234")
                    }
                }
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
            is AddPictureAdapter.SecondViewHolder -> holder.bind(item, position)
        }
    }

    fun setData(products: List<PictureEntity>) {
        picturelist = products as ArrayList<PictureEntity>
        notifyDataSetChanged()
        //아 마찬가지로 이거쓰면 ㄱㅊ할듯한데그냥 list안쓰고 흠흠
    }


    override fun getItemCount(): Int {
        return picturelist.size

    }


    //드래그 앤 드롭 파트
    /*
        fun onItemMove(fromPosition: Int, toPosition: Int) {
            if (fromPosition < toPosition) {
                for (i in fromPosition until toPosition) {
                    Collections.swap(picturelist, i, i + 1)

                }
            } else {
                for (i in fromPosition downTo toPosition + 1) {
                    Collections.swap(picturelist, i, i - 1)
                }
            }
            //두 개 맞게 잘 바뀜 근데, 문제는 DB는 그대로라서 어림도 없다는 정도..?
            //order을 추가하고 이를 통해서 순서를 바꿔야함
            //다시 setData하게 해야하나ㅋㅋ
            notifyItemMoved(fromPosition, toPosition)
        }
    */

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(picturelist, i, i + 1)
                updateOrder(i, i + 1) // 순서 업데이트
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(picturelist, i, i - 1)
                updateOrder(i, i - 1) // 순서 업데이트
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    private fun updateOrder(fromPosition: Int, toPosition: Int) {
        val fromItem = picturelist[fromPosition]
        val toItem = picturelist[toPosition]

        val fromOrder = fromItem.order
        fromItem.order = toItem.order
        toItem.order = fromOrder

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                pictureDB!!.getPictureDao().updatePictureOrders(fromItem, toItem)
            }
        }
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        picturelist.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, picturelist.size)
    }

}


