package com.example.people_here.Search

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.MainCourseMapData
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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions


class MainTourCourseFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMainTourCourseBinding
    private var mainCourseMapData : ArrayList<MainCourseMapData> = arrayListOf()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mainTourCourseAdapter: MainTourCourseAdapter?= null
    private var googleMap: GoogleMap? = null
    private var polyline: Polyline? = null
    private val markers = mutableListOf<Marker>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainTourCourseBinding.inflate(layoutInflater)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //백앤드 통신 시 변경될 데이터 추가 방식입니다.
        initRecyclerview()
        initDummyData()

        initSwipeRecyclerView()

        return binding.root
    }

    private val locationSets = listOf(
        listOf(
            LatLng(37.5409, 127.078),   // 1번째 아이템, 1세트
            LatLng(37.5416, 127.085),
            LatLng(37.5424, 127.0799),
            LatLng(37.5429, 127.0793)
        ),
        listOf(
            LatLng(37.5419, 127.088),   // 1번째 아이템, 1세트
            LatLng(37.5426, 127.095),
            LatLng(37.5434, 127.0899),
            LatLng(37.5439, 127.0893)
        ),
        listOf(
            LatLng(37.5429, 127.098),   // 1번째 아이템, 1세트
            LatLng(37.5436, 127.105),
            LatLng(37.5444, 127.0999),
            LatLng(37.5449, 127.0993)
        )
    )

    private fun initSwipeRecyclerView() {
        binding.rvMainTourCourse.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                    if (position != RecyclerView.NO_POSITION) {
                        updateMap(position)
                    }
                }
            }
        })
    }

    private fun updateMap(position: Int) {
        // 기존 마커와 폴리라인 제거
        markers.forEach { it.remove() }
        markers.clear()
        polyline?.remove()

        // 새로운 마커와 폴리라인 추가
        if (position < locationSets.size) {
            val points = locationSets[position]
            points.forEach {
                val marker = googleMap?.addMarker(MarkerOptions().position(it))
                marker?.let { markers.add(it) }
            }

            // 폴리라인 그리기
            polyline = googleMap?.addPolyline(PolylineOptions().addAll(points).color(Color.RED))

            // 지도를 첫 번째 마커의 위치로 이동시키기
            if (points.isNotEmpty()) {
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(points[0], 15f))
            }
        }
    }


    private fun initDummyData() {
        mainCourseMapData.addAll(
            arrayListOf(
                MainCourseMapData("홍대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요","1시간","10000원", R.drawable.img_example_user, "Alexan", R.drawable.img_example, "홍대"),
                MainCourseMapData("건대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요","2시간","20000원", R.drawable.img_example_user, "Alexan", R.drawable.img_example, "홍대"),
                MainCourseMapData("이대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요","3시간","30000원", R.drawable.img_example_user, "Alexan", R.drawable.img_example, "홍대"),
                MainCourseMapData("중대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요","4시간","40000원", R.drawable.img_example_user, "Alexan", R.drawable.img_example, "홍대"),
                MainCourseMapData("고대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요","5시간","5000원", R.drawable.img_example_user, "Alexan", R.drawable.img_example, "홍대"),
                MainCourseMapData("연대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요","6시간","60000원", R.drawable.img_example_user, "Alexan", R.drawable.img_example, "홍대"),
                MainCourseMapData("성대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요","7시간","70000원", R.drawable.img_example_user, "Alexan", R.drawable.img_example, "홍대"),
                MainCourseMapData("서강대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요","8시간","80000원", R.drawable.img_example_user, "Alexan", R.drawable.img_example, "홍대"),
                MainCourseMapData("경희대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요","9시간","90000원", R.drawable.img_example_user, "Alexan", R.drawable.img_example, "홍대"),
                MainCourseMapData("시립대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요","10시간","100000원", R.drawable.img_example_user, "Alexan", R.drawable.img_example, "홍대")
            )
        )
    }

    private fun initRecyclerview() {
        mainTourCourseAdapter = MainTourCourseAdapter(mainCourseMapData)
        binding.rvMainTourCourse.adapter = mainTourCourseAdapter
        binding.rvMainTourCourse.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvMainTourCourse)
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
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        updateMap(0)
    }

}
