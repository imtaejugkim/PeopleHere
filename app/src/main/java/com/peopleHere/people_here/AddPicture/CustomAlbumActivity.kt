package com.peopleHere.people_here.AddPicture

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.peopleHere.people_here.databinding.ActivityCustomAlbumBinding
import com.peopleHere.people_here.Data.CustomAlbumData

class
CustomAlbumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomAlbumBinding
    private var customAlbumAdapter: CustomAlbumAdapter? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomAlbumBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val imageList = loadImages()
        customAlbumAdapter = CustomAlbumAdapter(this,imageList)
        binding.rvPhoto.adapter = customAlbumAdapter
        binding.rvPhoto.layoutManager =
            GridLayoutManager(this, 3)
        customAlbumAdapter!!.setOnItemClickListener(object :
            CustomAlbumAdapter.OnItemClickListener {
            override fun onItemClick(picturelist: CustomAlbumData) {
                //눌리면 이제 AddpicActivity에 사진 추가가 되게
                //TODO: RoomDB로 보내야 사용이 가능 할 듯
            }
        })

        binding.tvAdd.setOnClickListener {
            //intent로 넘기기
            val intent = Intent(this, AddPictureActivity::class.java)
            for (i in 0 until customAlbumAdapter!!.uriList.size) {//null~5일듯
                // 각 아이템에 대한 처리
                val uri = customAlbumAdapter!!.uriList[i]
                Log.d("qwer",uri)
                intent.putExtra("uri_$i", uri)
                //이렇게 하면 uri_1~5까지 string 형태로 받음
                }
            startActivity(intent)
            finish()

        }

    }

    private fun loadImages(): List<CustomAlbumData> {
        // 이미지 URI 리스트를 저장할 MutableList를 생성
        val imageList: MutableList<CustomAlbumData> = mutableListOf()

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
                imageList.add(
                    CustomAlbumData(
                        contentUri.toString(),
                        "Image Name"
                    )
                ) // CustomAlbumData에 URI와 이름 저장
            }
        }

        return imageList
    }
}