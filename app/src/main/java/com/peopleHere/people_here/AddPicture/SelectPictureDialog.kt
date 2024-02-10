package com.peopleHere.people_here.AddPicture

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.peopleHere.people_here.databinding.DialogSelectPictureBinding


class SelectPictureDialog(context: Context, private val activity: FragmentActivity) :
    Dialog(context) {
    private lateinit var binding: DialogSelectPictureBinding
    private var imageUri: Uri? = null

    /*
        private val requestPermissionLauncher: ActivityResultLauncher<String> =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    openGallery()
                }
            }
    */


    /*
        private val pickImageLauncher: ActivityResultLauncher<Intent> =
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    data?.data?.let {
                        imageUri = it
                        // imageView.setImageURI(imageUri)
                    }
                }
            }
    */

    private fun openGallery() {
        //여기 뭘까?? Action_pict이랑 midea 어쩌구

        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

        //   pickImageLauncher.launch(gallery)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSelectPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvOuter.setBackgroundColor(Color.TRANSPARENT)

        // Context를 FragmentActivity로 변환하여 권한을 요청
        binding.tvMyGallery.setOnClickListener {
            //권한 요청하고 갤러리로 이동 권한 없으면 권한 달라고 하는 팝업 띄우기
            requestPermissions()

        /* if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                 openGallery()
             } else {
                 requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        }*/

        }

        // 배경 투명 설정
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun requestPermissions() {

        //sdk 버전 32이상인 경우만 READ_MEDIA_IMAGES
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES)
            != PackageManager.PERMISSION_GRANTED
        ) {

            // 권한이 없는 경우 권한 요청
            //왜 이거 ActivityCompat 이 회색으로 뜨냐
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                99//이건 임의로 적은 것
            )


        }
    }
}
