package com.example.people_here.AddPicture

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import com.example.people_here.Data.AddPictureData
import com.example.people_here.R
import com.example.people_here.TitleCategory.TitleActivity
import com.example.people_here.databinding.ActivityAddPictureBinding


class AddPictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPictureBinding
    private var addPictureAdapter: AddPictureAdapter? = null
    val picturelist = arrayListOf<AddPictureData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPictureBinding.inflate(layoutInflater)
        CreateTextView()//text는 딱 oncreate 처음 한 번에만 보이면 된다

        val resourceUri_1 = Uri.parse("android.resource://$packageName/${R.drawable.photoaddition}")
        val resourceUri_2 = Uri.parse("android.resource://$packageName/${R.drawable.photoaddition2}")
        val resourceUri_full = Uri.parse("android.resource://$packageName/${R.drawable.photoaddition3}")

        //TODO:돌 떄 마다, ROom


        //Data 받아와서 추가하는 부분
        val receivedIntent = intent
        if (receivedIntent != null) {
            // ArrayList 크기만큼 반복
            var i = 0
            while (receivedIntent.hasExtra("uri_$i")) {
                val uriString = receivedIntent.getStringExtra("uri_$i").toString()
                Log.d("test1",uriString)
                val uri = Uri.parse(uriString)
                picturelist.add(AddPictureData(uriString.toString(),1))
                i++
            }
        }
        //첫 번째만 주황사진, 나머지는 회색 사진으로 바꿈
        if(picturelist.isEmpty()){
            picturelist.add(AddPictureData(resourceUri_1.toString(),0))
        }else{
            picturelist.add(AddPictureData(resourceUri_2.toString(),0))
            binding.btnNext.setBackgroundResource(R.drawable.add_list_next_button)
        }

        addPictureAdapter = AddPictureAdapter(picturelist)
        binding.rvPictures.adapter = addPictureAdapter
        binding.rvPictures.layoutManager =
            GridLayoutManager(this, 2)
        addPictureAdapter!!.setOnItemClickListener(object :
            AddPictureAdapter.OnItemClickListener {
            override fun onItemClick(picturelist: AddPictureData) {
                //눌리면 이제 AddpicActivity에 사진 추가가 되게
                //TODO: Intent로 넘겼는데 이거 저장때문에 roomDB에 넣어야함
            }
        })
        binding.btnNext.setOnClickListener {
            val intent = Intent(this, TitleActivity::class.java)
            startActivity(intent)
            //finish()
        }
           //TODO:1개 이상 추가 되면 화면 바뀌는..
        addPictureAdapter!!.notifyItemInserted(picturelist.size)

        binding.btnAddPicture.setOnClickListener {
            val bottomsheet = LocationChooseFragment()
            bottomsheet.show(supportFragmentManager, bottomsheet.tag)
        }



        setContentView(binding.root)
    }




    override fun onStart() {//아마 fragment에서 dissmiss하면 여기로 와질듯?
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

    }

    private fun CreateTextView() {
        //실험삼아 하나만 해 봅시당
        //arrayof로 해서 배열 만들고, 받아 오는대로 여기에 텍스트 추가해서 하면 될 듯?
        //마찬가지로 image하는데 이거 사진추가가 계속 위치가 바뀌는거 어떻게하는지 ㅋㅋ

        //결국, RoomDB로 받아서 거기에 imgaelist 추가추가 해서 불러오게 해야할 듯 지금은 서버 연동 전 이니까 shared로 해볼까

        val tv2 = TextView(this)
        tv2.setText("건대 화양식당")
        //for문으로 position에 따라 위치 수정해주면 될 듯 하다
        tv2.textSize = 14f
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
        )

        tv2.setTextColor(Color.parseColor("#9FA4A9"))

        binding.layoutLocation.addView(tv2)

    }

    private fun CreateImageView() {
        //TODO:arrayof로 해서 배열 만들고, 받아 오는대로 여기에 텍스트 추가해서 하면 될 듯?
        //마찬가지로 image하는데 이거 사진추가가 계속 위치가 바뀌는거 어떻게하는지 ㅋㅋ
        //여기서 image의 uri를 받아와서 iv로 감싸게 해서 텍스트처럼 추가하기

        val img1 = ImageView(this)
        //val imgString = ApplicationClass.mSharedPreferencesManager.getString("image", null)
        //val imgUri = Uri.parse(imgString)
        //Log.d("imgtest", imgUri.toString())
        //img1.setImageURI(imgUri)//이미지 띄웠으니 크기
        //나중에 roomdb로 바꿔서 하기


        val layoutParams = GridLayout.LayoutParams().apply {
            width = resources.getDimensionPixelSize(R.dimen.image_width)
            height = resources.getDimensionPixelSize(R.dimen.image_width) // 필요에 따라 높이도 설정
            // 그리드에서의 위치 설정 (rowSpec, colSpec)
            //추가되는거 무조건 0,0에다가

            //자동으로 밀리는게 아니라 겹쳐지네..?
            //근데 반복문 써서 위치 옮기면 터질텐디

            rowSpec = GridLayout.spec(0)
            columnSpec = GridLayout.spec(0)
        }

//        binding.layoutAddPic.addView(img1, layoutParams)

    }
}