package com.example.people_here.Search

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
    private val markerDataMap = hashMapOf<Marker, List<MainCourseMapData>>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mainTourCourseAdapter: MainTourCourseAdapter?= null
    private var googleMap: GoogleMap? = null
    private var polyline: Polyline? = null
    private val markers = mutableListOf<Marker>()
    private var isRecyclerViewMoved = false



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

        initMoveRecyclerView()

        return binding.root
    }

    private fun mapListener(){
        googleMap?.setOnMapClickListener {
            resetMarker()
            polyline?.remove()
            Log.d("map 클릭됨","map 클릭됨")
            moveRecyclerView(true, 400f)
        }
    }

    private fun markerListener(){
        googleMap?.setOnMarkerClickListener { marker ->
            resetMarker()
            moveRecyclerView(true, 200f)
            marker.setIcon(vectorToBitmap(R.drawable.ic_main_marker_clicked))

            val dataForMarker = markerDataMap[marker]
            dataForMarker?.let {
                mainTourCourseAdapter?.setData(it)
            }

            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15f));
            Log.d("marker 클릭됨","marker 클릭됨")
            true
        }
    }

    private fun resetMarker() {
        for (marker in markers) {
            marker.setIcon(vectorToBitmap(R.drawable.ic_main_marker_unclicked))
        }
    }

    private fun initMarkerData() {
        for (i in dummyLocation.indices) {
            val markerDataList = generateDataForMarker(i) // i번째 마커에 대한 데이터 리스트 생성
            val marker = addCustomMarker(dummyLocation[i][0],
                if (i == 0) R.drawable.ic_main_marker_clicked else R.drawable.ic_main_marker_unclicked)
            markers.add(marker)
            markerDataMap[marker] = markerDataList
        }
    }
    private fun generateDataForMarker(index: Int): List<MainCourseMapData> {
        return arrayListOf(
            MainCourseMapData("데이터 $index-1", "1시간", "10000원", R.drawable.img_example_user, "Alexan", R.drawable.img_example, "장소 $index"),
            MainCourseMapData("데이터 $index-2", "2시간", "20000원", R.drawable.img_example_user, "Alexan", R.drawable.img_example, "장소 $index") ,
            MainCourseMapData("데이터 $index-3", "2시간", "20000원", R.drawable.img_example_user, "Alexan", R.drawable.img_example, "장소 $index") ,
            MainCourseMapData("데이터 $index-4", "2시간", "20000원", R.drawable.img_example_user, "Alexan", R.drawable.img_example, "장소 $index")

        )
    }

    private fun moveRecyclerView(moveDown: Boolean, distance: Float) {
        isRecyclerViewMoved = moveDown
        val translationY = if (moveDown) distance
        else distance

        // RecyclerView 이동 애니메이션
        binding.rvMainTourCourse.animate()
            .translationY(translationY)
            .setDuration(300)
            .start()
    }

    private val dummyLocation = listOf(
        listOf(
            LatLng(37.5421, 127.0736),
            LatLng(37.5405, 127.0745),
            LatLng(37.5419, 127.0777),
            LatLng(37.5402, 127.0803)
        ),
        listOf(
            LatLng(37.5421, 127.0736),
            LatLng(37.5431, 127.0716),
            LatLng(37.5442, 127.0753),
            LatLng(37.5456, 127.0726)
        ),
        listOf(
            LatLng(37.5421, 127.0736),
            LatLng(37.5505, 127.0845),
            LatLng(37.5519, 127.0877),
            LatLng(37.5502, 127.0903)
        )
    )

    private fun initMoveRecyclerView() {

        mainTourCourseAdapter?.setOnItemClickListener(object : MainTourCourseAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                if (isRecyclerViewMoved) {
                    moveRecyclerView(false, 0f)
                }
            }
        })

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
        // 기존 마커와 선 제거
        markers.forEach { it.remove() }
        markers.clear()
        polyline?.remove()

        // 새로운 마커와 선 추가
        if (position < dummyLocation.size) {
            val points = dummyLocation[position]
            for (i in points.indices) {
                val markerIcon = if (i == 0) {
                    R.drawable.ic_main_marker_clicked
                } else {
                    R.drawable.ic_main_marker_unclicked
                }

                val marker = addCustomMarker(points[i], markerIcon)
                markers.add(marker)
            }

            polyline = googleMap?.addPolyline(PolylineOptions().addAll(points).color(Color.BLACK).width(3f))

            // 지도는 첫번 째 코스 기준 이동
            if (points.isNotEmpty()) {
                googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(points[0], 15f));
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

    private fun addCustomMarker(location: LatLng, drawableId: Int): Marker {
        val markerOptions = MarkerOptions()
            .position(location)
            .icon(vectorToBitmap(drawableId))
        return googleMap?.addMarker(markerOptions) ?: throw IllegalStateException("구글 맵 오류 예외 처리")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        updateMap(0)

        initMarkerData()

        mapListener()
        markerListener()

    }
}
