package com.example.people_here.AddPicture

import android.content.Context
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.people_here.ApplicationClass
import com.example.people_here.CostInput.CostFragment
import com.example.people_here.MakingTour.HelpDiaLog
import com.example.people_here.R
import com.example.people_here.databinding.ActivityAddPictureBinding

class AddPictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPictureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPictureBinding.inflate(layoutInflater)
        CreateTextView()//text는 딱 oncreate 처음 한 번에만 보이면 된다

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
        CreateImageView()//일단 켜질때마다 사진이업데이트 되는 오류가 있을 수 있으니 주의해서 개발하기 shared에 잘 이어지게

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
        //실험삼아 하나만 해 봅시당
        //arrayof로 해서 배열 만들고, 받아 오는대로 여기에 텍스트 추가해서 하면 될 듯?
        //마찬가지로 image하는데 이거 사진추가가 계속 위치가 바뀌는거 어떻게하는지 ㅋㅋ

        //결국, RoomDB로 받아서 거기에 imgaelist 추가추가 해서 불러오게 해야할 듯 지금은 서버 연동 전 이니까 shared로 해볼까?
        //여기서 image의 uri를 받아와서 iv로 감싸게 해서 텍스트처럼 추가하기
        val img1 = ImageView(this)
        val imgString = ApplicationClass.mSharedPreferencesManager.getString("image", null)
        val imgUri = Uri.parse(imgString)
        Log.d("imgtest", imgUri.toString())
        img1.setImageURI(imgUri)//이미지 띄웠으니 크기
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

        binding.layoutAddPic.addView(img1, layoutParams)

    }
}