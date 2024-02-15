package com.peopleHere.people_here.AddPicture

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.peopleHere.people_here.ApplicationClass
import com.peopleHere.people_here.databinding.FragmentSelectPictureBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SelectPictureFragment() :  BottomSheetDialogFragment() {

    private var imageUri: Uri? = null

    private lateinit var binding: FragmentSelectPictureBinding
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
            }
        }

    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {//이미지가 선택된 경우이다
                val data: Intent? = result.data//받아온 데이터를 저장함

                data?.data?.let {
                    imageUri = it
                    //shraed로 data저장 해놓기

                    ApplicationClass.mSharedPreferencesManager.edit().putString("image",imageUri.toString()).apply()
                    val c= ApplicationClass.mSharedPreferencesManager.getString("image",null)
                    dismiss()



                    //activity에 데이터 넘겨주고 startActivity 혹은 dismiss?

                    //intent를  통해 이전에 주고 dismiss하는건?
                }
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSelectPictureBinding.inflate(layoutInflater)

        binding.clFirst.setOnClickListener{
            //갤러리 호출
            //TODO: 왜 요청 개같이뜨나요?
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)

            requestPermissions()
            //왜 한참뒤에 뜸?
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)

            val intent = Intent(requireContext(), CustomAlbumActivity::class.java)
            startActivity(intent)
            requireActivity().finish()


            //TODO:권한 요청 좀 다듬기
            //??왜안뜸?
//            openGallery()//갤러리 열음


        }



        return binding.root

    }
    private fun requestPermissions() {

        //sdk 버전 32이상인 경우만 READ_MEDIA_IMAGES
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_MEDIA_IMAGES)
            != PackageManager.PERMISSION_GRANTED
        ) {

            // 권한이 없는 경우 권한 요청
            //왜 이거 ActivityCompat 이 회색으로 뜨냐
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                99//이건 임의로 적은 것
            )


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