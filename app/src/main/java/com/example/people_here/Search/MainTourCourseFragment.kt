package com.example.people_here.Search

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.people_here.R
import com.example.people_here.databinding.FragmentMainTourCourseBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainTourCourseFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMainTourCourseBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainTourCourseBinding.inflate(layoutInflater)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initRecyclerview()

        return binding.root
    }

    private fun initRecyclerview() {
        TODO("Not yet implemented")
    }

    // SVG 파일을 Bitmap으로 변환
    private fun vectorToBitmap(drawableId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(requireContext(), drawableId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    // 변환된 Bitmap을 마커 아이콘으로 사용
    private fun updateLocationUI() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 위치 권한이 없는 경우, 권한 요청
            return
        }

        googleMap?.apply {
            isMyLocationEnabled = false
            uiSettings.isMyLocationButtonEnabled = false

            val konkukUniversity = LatLng(37.5409, 127.078)
            moveCamera(CameraUpdateFactory.newLatLngZoom(konkukUniversity, 15f))

            // SVG 파일을 Bitmap으로 변환하고 마커 아이콘으로 설정
            val customMarkerIcon = vectorToBitmap(R.drawable.ic_main_course_map)
            addMarker(MarkerOptions()
                .position(konkukUniversity)
                .title("건국대학교")
                .icon(customMarkerIcon))
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        updateLocationUI()
    }
}
