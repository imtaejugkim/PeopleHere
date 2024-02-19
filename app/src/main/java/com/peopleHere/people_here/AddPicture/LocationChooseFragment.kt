package com.peopleHere.people_here.AddPicture

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.peopleHere.people_here.ApplicationClass
import com.peopleHere.people_here.Data.LocationChooseData
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.FragmentLocationChooseBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.peopleHere.people_here.AddPicture.PictureDB.PictureDB
import com.peopleHere.people_here.MakingTour.AddListDataManager

class LocationChooseFragment : BottomSheetDialogFragment() {

    private var imageUri: Uri? = null
    private var locationChooseAdapter: LocationChooseAdapter? = null
    var isClicked:Boolean=false
    private var locationList : ArrayList<LocationChooseData> = arrayListOf()
    var savedLocationName=""
    var savedPictureNum=0


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



        for(i in 0 until AddListDataManager.addListData.size){
            locationList.add(LocationChooseData(AddListDataManager.addListData[i].placeImage,
                AddListDataManager.addListData[i].placeName,
                false,0))
            Log.d("장소사진",AddListDataManager.addListData[i].placeImage)
            Log.d("이름",AddListDataManager.addListData[i].placeName)
        }



        locationChooseAdapter = LocationChooseAdapter(locationList, requireActivity())

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
            //TODO:여기가 문제다 이름 앞에서 받아오는게 아니라 임의로 이따구로 쳐 받아 오니까 안되는거지
            override fun clickLocation(locationName: String, picturNum: Int) {
                savedLocationName=locationName
                savedPictureNum=picturNum
            }
        })

        binding.ivOut.setOnClickListener {
            dismiss()
        }
        binding.btnChoose.setOnClickListener {
            if(isClicked){
                if(savedPictureNum>=5){
                    val toastLayout = LayoutInflater.from(binding.root.context)
                        .inflate(
                            R.layout.toast_max_location,
                            null
                        ) // R.layout.custom_toast_layout은 사용자가 정의한 레이아웃 파일입니다.
                    val toast = Toast(binding.root.context)
                    toast.view = toastLayout
                    toast.setGravity(Gravity.BOTTOM, 0, 80.dpToPx()) // 80dp 아래로
                    toast.show()
                }else{
                    val bottomsheet = SelectPictureFragment(savedLocationName,savedPictureNum)
                    bottomsheet.show(childFragmentManager, bottomsheet.tag)

                }



            }



        }


    }
    fun Int.dpToPx(): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (this * scale).toInt()
    }

}
