package com.example.people_here.AddPicture

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.people_here.ApplicationClass
import com.example.people_here.R
import com.example.people_here.databinding.DialogSelectPictureBinding
import com.example.people_here.databinding.FragmentSelectPictureBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SelectPictureFragment :  BottomSheetDialogFragment() {

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

            val intent = Intent(requireContext(), CustomAlbumActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            //TODO:권한 요청 좀 다듬기
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
//            openGallery()//갤러리 열음


        }


        return binding.root

    }
    private fun openGallery() {
        val gallery = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )//내부 저장소에 가서 이미지를 불러오는 코드
        pickImageLauncher.launch(gallery)//이미지세팅
    }



}