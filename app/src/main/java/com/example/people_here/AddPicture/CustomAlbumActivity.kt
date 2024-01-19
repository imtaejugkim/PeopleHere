package com.example.people_here.AddPicture

import android.content.ContentUris
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.people_here.Data.CustomAlbumData
import com.example.people_here.Data.LocationChooseData
import com.example.people_here.R
import com.example.people_here.databinding.ActivityCustomAlbumBinding
import com.example.people_here.databinding.FragmentLocationChooseBinding

class CustomAlbumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomAlbumBinding
    private var customAlbumAdapter: CustomAlbumAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCustomAlbumBinding.inflate(layoutInflater)

        setContentView(binding.root)
        customAlbumAdapter = CustomAlbumAdapter(ArrayList())
        binding.rvPhoto.adapter = customAlbumAdapter
        binding.rvPhoto.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        customAlbumAdapter!!.setOnItemClickListener(object :
            CustomAlbumAdapter.OnItemClickListener {
            override fun onItemClick(picturelist: CustomAlbumData) {
            //눌리면 이제 AddpicActivity에 사진 추가가 되게

            }
        })

    }

    private fun loadImages() {
        // 이미지 URI 리스트를 저장할 MutableList를 생성
        val imageList: MutableList<Uri> = mutableListOf()

        // 쿼리할 컬럼들을 정의
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
        )


        // 이미지를 가져올 때 정렬 순서를 정의
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"


        // 외부 저장소에 있는 이미지들에 대한 쿼리를 수행
        val query = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )


        // 쿼리 결과를 사용하기 위해 use 블록 사용
        query?.use { cursor ->
            // 커서에서 ID 컬럼의 인덱스를 가져옴
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            // 커서를 반복하여 각 이미지의 ID를 가져와 URI를 생성하여 리스트에 추가
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                imageList.add(contentUri)
            }
        }


    }


}