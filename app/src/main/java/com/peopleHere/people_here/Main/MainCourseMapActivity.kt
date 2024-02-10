package com.peopleHere.people_here.Main

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.Data.MainCourseMapData
import com.peopleHere.people_here.R
import com.peopleHere.people_here.CourseContents.CourseContentsActivity
import com.peopleHere.people_here.databinding.ActivityMainTourCourseBinding
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


class MainCourseMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainTourCourseBinding
    private var mainCourseMapData : ArrayList<MainCourseMapData> = arrayListOf()
    private val markerDataMap = hashMapOf<Marker, List<MainCourseMapData>>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mainTourCourseAdapter: MainTourCourseAdapter?= null
    private var googleMap: GoogleMap? = null
    private var polyline: Polyline? = null
    private val markers = mutableListOf<Marker>()
    private var isRecyclerViewMoved = false

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTourCourseBinding.inflate(layoutInflater)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MainCourseMapActivity)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }

        //백앤드 통신 시 변경될 데이터 추가 방식입니다.
        initRecyclerview()
        initDummyData()

        initMoveRecyclerView()

        setContentView(binding.root)
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // 권한 요청 이유 설명하는 코드?
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 승인 시 해당 위치로 이동 하는 코드?
            } else {
                // 권한 거부 시 메인으로 아마 다시 돌아가야 할듯?
            }
        }
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
        val arrayList1 = ArrayList<String>()
        arrayList1.add("성동구")
        arrayList1.add("팔달구")
        arrayList1.add("영통구")

        return arrayListOf(
            MainCourseMapData("홍대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,1, R.drawable.img_example_user, "Alex", R.drawable.img_example, "홍대역 근처"),
            MainCourseMapData("건대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,2, R.drawable.img_example_user, "Brian", R.drawable.img_example_user, "건대역"),
            MainCourseMapData("이대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,3, R.drawable.img_example_user, "Cyan", R.drawable.img_example, "이대역"),
            MainCourseMapData("중대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,4, R.drawable.img_example_user, "David", R.drawable.img_example_user, "중대역"),
            MainCourseMapData("고대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,3, R.drawable.img_example_user, "Emily", R.drawable.img_example, "홍대"),
            MainCourseMapData("연대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,2, R.drawable.img_example_user, "Frank", R.drawable.img_example_user, "홍대"),
            MainCourseMapData("성대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,1, R.drawable.img_example_user, "Green", R.drawable.img_example, "홍대"),
            MainCourseMapData("서강대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,2, R.drawable.img_example_user, "Harry", R.drawable.img_example_user, "홍대"),
            MainCourseMapData("경희대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,3, R.drawable.img_example_user, "Iron", R.drawable.img_example, "홍대"),
            MainCourseMapData("시립대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,4, R.drawable.img_example_user, "Jessica", R.drawable.img_example_user, "홍대")
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
        val arrayList1 = ArrayList<String>()
        arrayList1.add("성동구")
        arrayList1.add("팔달구")
        arrayList1.add("영통구")
        mainCourseMapData.addAll(
            arrayListOf(
                MainCourseMapData("홍대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,1, R.drawable.img_example_user, "Alex", R.drawable.img_example, "홍대역 근처"),
                MainCourseMapData("건대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,2, R.drawable.img_example_user, "Brian", R.drawable.img_example_user, "건대역"),
                MainCourseMapData("이대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,3, R.drawable.img_example_user, "Cyan", R.drawable.img_example, "이대역"),
                MainCourseMapData("중대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,4, R.drawable.img_example_user, "David", R.drawable.img_example_user, "중대역"),
                MainCourseMapData("고대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,3, R.drawable.img_example_user, "Emily", R.drawable.img_example, "홍대"),
                MainCourseMapData("연대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,2, R.drawable.img_example_user, "Frank", R.drawable.img_example_user, "홍대"),
                MainCourseMapData("성대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,1, R.drawable.img_example_user, "Green", R.drawable.img_example, "홍대"),
                MainCourseMapData("서강대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,2, R.drawable.img_example_user, "Harry", R.drawable.img_example_user, "홍대"),
                MainCourseMapData("경희대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,3, R.drawable.img_example_user, "Iron", R.drawable.img_example, "홍대"),
                MainCourseMapData("시립대에서 만나는 디자인과 예술인데 얘는 데이터가 좀 길어요",arrayList1,4, R.drawable.img_example_user, "Jessica", R.drawable.img_example_user, "홍대")
            )
        )
    }

    private fun initRecyclerview() {
        mainTourCourseAdapter = MainTourCourseAdapter(mainCourseMapData)
        binding.rvMainTourCourse.adapter = mainTourCourseAdapter
        binding.rvMainTourCourse.layoutManager = LinearLayoutManager(this@MainCourseMapActivity,
            LinearLayoutManager.HORIZONTAL, false)

        mainTourCourseAdapter?.setOnItemClickListener(object : MainTourCourseAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MainCourseMapActivity, CourseContentsActivity::class.java)

                val itemData = mainCourseMapData[position]
                intent.putExtra("key", itemData)
                Log.d("클릭됨","클릭됨")

                startActivity(intent)
            }
        })

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvMainTourCourse)
    }

    // SVG 파일을 Bitmap으로 변환
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
        updateMap(0)

        initMarkerData()

        mapListener()
        markerListener()

    }

    override fun onStart() {
        super.onStart()
        requestLocationPermission()
    }
}
