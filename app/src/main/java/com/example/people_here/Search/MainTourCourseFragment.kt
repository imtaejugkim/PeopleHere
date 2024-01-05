package com.example.people_here.Search

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.people_here.R
import com.example.people_here.databinding.FragmentMainTourCourseBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
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

        return binding.root
    }

    private fun updateLocationUI() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 위치 권한이 없는 경우, 권한 요청
            return
        }

        googleMap?.apply {
            isMyLocationEnabled = false // 기기의 현재 위치 대신 고정된 위치를 사용
            uiSettings.isMyLocationButtonEnabled = false

            // 건국대학교의 좌표를 사용하여 지도의 위치 설정
            val konkukUniversity = LatLng(37.5409, 127.078)
            moveCamera(CameraUpdateFactory.newLatLngZoom(konkukUniversity, 15f))

            // 건국대학교 위치에 마커 추가
            addMarker(MarkerOptions().position(konkukUniversity).title("건국대학교"))
        }
    }


    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        updateLocationUI()
    }
}
