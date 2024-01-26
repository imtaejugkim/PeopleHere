package com.example.people_here.AddPicture

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.people_here.ApplicationClass
import com.example.people_here.CostInput.CostExitDialog
import com.example.people_here.CostInput.CostFragment
import com.example.people_here.CostInput.PlaceCostAdapter
import com.example.people_here.Data.LocationChooseData
import com.example.people_here.Data.PlaceCostData
import com.example.people_here.R
import com.example.people_here.databinding.FragmentLocationChooseBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LocationChooseFragment : BottomSheetDialogFragment() {

    private var imageUri: Uri? = null
    private var locationChooseAdapter: LocationChooseAdapter? = null
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
            }
        }

    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {//이미지가 선택된 경우이다
                val data: Intent? = result.data//받아온 데이터를 저장함

                data?.data?.let {
                    imageUri = it
                    //shraed로 data저장 해놓기

                    ApplicationClass.mSharedPreferencesManager.edit().putString("image",imageUri.toString()).apply()
                    val c=ApplicationClass.mSharedPreferencesManager.getString("image",null)
                    dismiss()

                    //activity에 데이터 넘겨주고 startActivity 혹은 dismiss?
                    //intent를  통해 이전에 주고 dismiss하는건?
                }
            }
        }

    private lateinit var binding: FragmentLocationChooseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentLocationChooseBinding.inflate(layoutInflater)
        usingAdapter()
        return binding.root

    }

    private fun usingAdapter() {

        val locationlist = arrayListOf(
            LocationChooseData(R.drawable.img, "장소1"),
            LocationChooseData(R.drawable.img, "장소1"),
            LocationChooseData(R.drawable.img, "장소1"),
            LocationChooseData(R.drawable.img, "장소1"),
            LocationChooseData(R.drawable.img, "장소1"),
            LocationChooseData(R.drawable.img, "장소1"),
            LocationChooseData(R.drawable.img, "장소1"),
            LocationChooseData(R.drawable.img, "장소1"),
            LocationChooseData(R.drawable.img, "장소1"),
            LocationChooseData(R.drawable.img, "장소2")
        )
        locationChooseAdapter = LocationChooseAdapter(locationlist)

        binding.rvChooseLocation.adapter = locationChooseAdapter
        binding.rvChooseLocation.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        locationChooseAdapter!!.setOnItemClickListener(object :
            LocationChooseAdapter.OnItemClickListener {
            override fun onItemClick(locationlist: LocationChooseData) {
                binding.btnChoose.setBackgroundResource(R.drawable.making_tour_button_next)





                // val bottomsheet= CostFragment()
                //bottomsheet.show(supportFragmentManager, bottomsheet.tag)
                //여기 프래그먼트 적용
                //bottomdialog 나오게
            }
        })


        binding.btnChoose.setOnClickListener {
            //여기서 이제 갤러리 접근이요
            //권한 요청 팝업이 안 뜬다..?그냥 거부됐다만 나옴
            val bottomsheet= SelectPictureFragment()
            bottomsheet.show(childFragmentManager, bottomsheet.tag)




            // val selectPictureDialog = SelectPictureDialog(requireContext(),requireActivity())

            //한 번 그냥 이것도 fragment로 해보기..ㅠㅠ
            //selectPictureDialog.getWindow()!!.setGravity(Gravity.BOTTOM);
            //selectPictureDialog.show()

            //또 다이어로그에선 생명주기떄매 안되네 ㅋㅋ
        }


    }

    private fun openGallery() {
        val gallery = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )//내부 저장소에 가서 이미지를 불러오는 코드
        pickImageLauncher.launch(gallery)//이미지세팅
    }

}
