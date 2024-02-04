package com.example.people_here.AddPicture

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.people_here.Data.CustomAlbumData
import com.example.people_here.databinding.ItemCustoAlbumBinding

class CustomAlbumAdapter( private val context: Context,val picturelist: List<CustomAlbumData>) :
    RecyclerView.Adapter<CustomAlbumAdapter.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private var CountItem:Int=0

    val uriList: ArrayList<String> = ArrayList()
    interface OnItemClickListener {
        fun onItemClick(picturelist: CustomAlbumData)
    }
    //조오옹졌당
    inner class ViewHolder(val binding: ItemCustoAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(picturelist: CustomAlbumData) {
            //여기 각각 갤러리에서 메타 데이터 받아와서 넣기
            Glide.with(binding.root)
                .load(Uri.parse(picturelist.imageUrl)) // URI 문자열을 URI로 변환
                .into(binding.ivProfile)
            val color = ColorStateList.valueOf(Color.parseColor("#FF7834"))
            //만약 여기서 이미 db에 있는 url 가지고 있으면 countItem 증가시키고 눌러지게 구성되게 하기


            binding.ivProfile.setOnClickListener {
                itemClickListener.onItemClick(picturelist)//하나의 객체 눌리게
                //각각 true면 false되게, false면 true되게

                if(CountItem==5 && !binding.selectRatioBT.isChecked){
                    //다이어로그 보여주기
                    val maxpictureDialog = MaxPictureDialog(context)
                    maxpictureDialog.show()
                    return@setOnClickListener
                    //그 다음꺼 안 눌ㄹ리게
                }


                if(!binding.selectRatioBT.isChecked){
                    binding.selectRatioBT.isChecked=true
                    CountItem++;
                    binding.selectRatioBT.buttonTintList = color
                    uriList.add(picturelist.imageUrl)
                    //TODO:issue: 쥰나 빠르게 하니까 6개가 되네 mutexlock??해야하나 설마 이렇게 빨리 누르는 사용자 없겠지^^
                    Log.d("countItem--",CountItem.toString())
                }else{
                    binding.selectRatioBT.isChecked=false
                    CountItem--;
                    removeUri(picturelist.imageUrl)
                    // 이전에 저장한 초기 색상으로 복원

                    Log.d("countItem++",CountItem.toString())
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
    fun removeUri(uriToRemove: String) {
        // 리스트를 순회하면서 동일한 URI를 찾아 제거
        val iterator = uriList.iterator()
        while (iterator.hasNext()) {
            val uri = iterator.next()
            if (uri == uriToRemove) {//스트링으로 비교해서 없애기
                iterator.remove()
                break // 중복된 URI가 없다고 가정하고, 찾으면 반복문을 종료합니다.
            }
        }
    }

    override fun getItemViewType(position: Int): Int { // 재활용시 눌리는거 막아줌
        return position
    }
    // 이미지 리스트를 설정하
}