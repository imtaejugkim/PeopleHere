package com.peopleHere.people_here.AddPicture

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.peopleHere.people_here.ApplicationClass
import com.peopleHere.people_here.Data.LocationChooseData
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.FragmentLocationChooseBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LocationChooseFragment : BottomSheetDialogFragment() {

    private var imageUri: Uri? = null
    private var locationChooseAdapter: LocationChooseAdapter? = null
    var isClicked:Boolean=false


    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {//이미지가 선택된 경우이다
                val data: Intent? = result.data//받아온 데이터를 저장함
                data?.data?.let {
                    imageUri = it
                    //shraed로 data저장 해놓기

                    ApplicationClass.mSharedPreferencesManager.edit()
                        .putString("image", imageUri.toString()).apply()
                    val c = ApplicationClass.mSharedPreferencesManager.getString("image", null)
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
            LocationChooseData(R.drawable.img, "장소1", false),
            LocationChooseData(R.drawable.img, "장소1", false),
            LocationChooseData(R.drawable.img, "장소1", false),
            LocationChooseData(R.drawable.img, "장소1", false),
            LocationChooseData(R.drawable.img, "장소1", false),
            LocationChooseData(R.drawable.img, "장소1", false),
            LocationChooseData(R.drawable.img, "장소1", false),
            LocationChooseData(R.drawable.img, "장소1", false),
            LocationChooseData(R.drawable.img, "장소1", false),
        )
        locationChooseAdapter = LocationChooseAdapter(locationlist)

        binding.rvChooseLocation.adapter = locationChooseAdapter
        binding.rvChooseLocation.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        locationChooseAdapter!!.setOnItemClickListener(object :
            LocationChooseAdapter.OnItemClickListener {
            override fun onItemClick(locationlist: LocationChooseData,checkSelected:Boolean) {

            isClicked=checkSelected
            if(isClicked){
                binding.btnChoose.setBackgroundResource(R.drawable.making_tour_button_next)
            }else{
                binding.btnChoose.setBackgroundResource(R.drawable.making_tour_button_close)

            }
                Log.d("isCliecked_fragment",isClicked.toString())
            }
        })


        binding.btnChoose.setOnClickListener {

            if(isClicked){
                val bottomsheet = SelectPictureFragment()
                bottomsheet.show(childFragmentManager, bottomsheet.tag)


            }



        }


    }

/*
    private fun openGallery() {
        val gallery = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )//내부 저장소에 가서 이미지를 불러오는 코드
        pickImageLauncher.launch(gallery)//이미지세팅
    }
*/

}
