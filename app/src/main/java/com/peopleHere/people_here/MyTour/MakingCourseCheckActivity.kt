package com.peopleHere.people_here.MyTour

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.peopleHere.people_here.MainActivity
import com.peopleHere.people_here.MakingTour.MakingTourAddListActivity
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivityMakingCourseCheckBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MakingCourseCheckActivity : AppCompatActivity() , OnMapReadyCallback{
    private lateinit var binding : ActivityMakingCourseCheckBinding
    private var googleMap: GoogleMap?= null
    private lateinit var placesClient: PlacesClient
    private var location: LatLng? = null
    var markerIcon = R.drawable.ic_main_marker_unclicked
    var imgFile : String ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakingCourseCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Places.initialize(applicationContext, "AIzaSyB3WLa8PNBaMOK2OQm1U64Hb6RetEu-E1g")
        placesClient = Places.createClient(this)

        val placeId = intent.getStringExtra("placeId")
        val placeName = intent.getStringExtra("placeName")
        val placeAddress = intent.getStringExtra("placeAddress")
        val placeLatitude = intent.getDoubleExtra("placeLatitude", 0.0)
        val placeLongitude = intent.getDoubleExtra("placeLongitude", 0.0)

        // 이름 정의
        binding.tvMainSearchRecentRegion.text = placeName
        binding.tvMainSearchRecentPlace.text = placeAddress

        // photo api
        val placeFields = listOf(Place.Field.PHOTO_METADATAS)
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        placesClient.fetchPlace(request).addOnSuccessListener { response ->
            val place = response.place
            val photoMetadatas = place.photoMetadatas
            if (photoMetadatas == null || photoMetadatas.isEmpty()) {
                Log.w("PhotoDemo", "No photo metadata.")
                return@addOnSuccessListener
            }

            val photoMetadata = photoMetadatas.first()
            val photoMetadataRequest = FetchPhotoRequest.builder(photoMetadata)
                .build()

            placesClient.fetchPhoto(photoMetadataRequest).addOnSuccessListener { fetchPhotoResponse ->
                val bitmap = fetchPhotoResponse.bitmap
                imgFile = bitmapToJPG(this, bitmap, "$placeName")
                runOnUiThread {
                    binding.ivPlace.setImageBitmap(bitmap)
                }
            }
        }.addOnFailureListener { exception ->
            if (exception is ApiException) {
                val statusCode = exception.statusCode
                Log.d("photo오류",statusCode.toString())
            }
        }

        // 마커 정의
        location = LatLng(placeLatitude,placeLongitude)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync (this)

        binding.btnReSelect.setOnClickListener {
            onBackPressed()
        }

        binding.ivCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnCorrect.setOnClickListener {
            val intent = Intent(this, MakingTourAddListActivity::class.java)
            intent.putExtra("placeImage", imgFile)
            intent.putExtra("placeId", placeId)
            intent.putExtra("placeName", placeName)
            intent.putExtra("placeAddress", placeAddress)
            intent.putExtra("placeLatitude", placeLatitude)
            intent.putExtra("placeLongitude", placeLongitude)
            startActivity(intent)
        }
    }

    fun bitmapToJPG (context: Context, bitmap: Bitmap, fileName: String): String {
        // 앱의 내부 저장소에 저장할 파일 경로를 정의합니다.
        val storageDir = context.getExternalFilesDir(null)
        val imageFile = File(storageDir, "$fileName.jpg")

        // 파일 출력 스트림을 사용하여 비트맵을 JPEG 형식으로 압축하고 저장합니다.
        var outputStream: OutputStream? = null
        try {
            outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) // JPEG 형식으로 압축
            outputStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            outputStream?.close()
        }

        // 저장된 이미지 파일의 절대 경로를 반환합니다.
        return imageFile.absolutePath
    }


    private fun vectorToBitmap(drawableId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(this, drawableId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun addCustomMarker(location: LatLng, drawableId: Int): Marker {
        val markerOptions = MarkerOptions()
            .position(location)
            .icon(vectorToBitmap(drawableId))
        return googleMap?.addMarker(markerOptions) ?: throw IllegalStateException("구글 맵 오류 예외 처리")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        location?.let {
            addCustomMarker(it, markerIcon)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
        }
    }

}