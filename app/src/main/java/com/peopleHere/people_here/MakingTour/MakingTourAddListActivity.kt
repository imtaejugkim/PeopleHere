package com.peopleHere.people_here.MakingTour

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peopleHere.people_here.Data.MakingTourAddListData
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivityMakingTourAddListBinding
import com.peopleHere.people_here.databinding.DialogMakingTourAddListSequenceBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.peopleHere.people_here.MyTour.MakingCourseSearchActivity

class MakingTourAddListActivity : AppCompatActivity() , OnMapReadyCallback, MakingTourAddListAdapter.OnItemCountChangedListener{
    lateinit var binding: ActivityMakingTourAddListBinding
    private var googleMap: GoogleMap?= null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var addListAdapter: MakingTourAddListAdapter? = null
    private var addListData: ArrayList<MakingTourAddListData> = arrayListOf()
    private var isEditMode: Boolean = false
    private var sequenceDialog: Dialog? = null
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private var marker : Marker ?= null
    var markerIcon = R.drawable.ic_main_marker_unclicked
    private var location: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakingTourAddListBinding.inflate(layoutInflater)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val placeImage = intent.getStringExtra("placeImage")
        val placeName = intent.getStringExtra("placeName")
        val placeAddress = intent.getStringExtra("placeAddress")
        val placeLatitude = intent.getDoubleExtra("placeLatitude", 0.0)
        val placeLongitude = intent.getDoubleExtra("placeLongitude", 0.0)

        location = LatLng(placeLatitude,placeLongitude)

        // 받은 데이터를 사용하여 RecyclerView 리스트 업데이트
        if (placeName != null && placeAddress != null && placeImage != null) {
            updateRecyclerView(placeImage, placeName, placeAddress, location!!)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setContentView(binding.root)

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                // 받은 데이터로 리스트 업데이트
                val newPlaceImage = data?.getStringExtra("placeImage")
                val newPlaceName = data?.getStringExtra("placeName")
                val newPlaceAddress = data?.getStringExtra("placeAddress")
                val newPlaceLatitude = data?.getDoubleExtra("placeLatitude", 0.0) ?: 0.0
                val newPlaceLongitude = data?.getDoubleExtra("placeLongitude", 0.0) ?: 0.0

                if (newPlaceName != null && newPlaceAddress != null) {
                    val newLocation = LatLng(newPlaceLatitude, newPlaceLongitude)
                    updateRecyclerView(newPlaceImage.toString(), newPlaceName, newPlaceAddress, newLocation)
                }
            }
        }

        initRecyclerView()
        setupItemTouchHelper()

        binding.btnMakingTourAddListEditSequence.setOnClickListener {
            initEditSequence()
        }

        binding.btnMakingTourAddListFinishSequence.setOnClickListener {
            initEditSequence()
        }

        binding.clAddList.setOnClickListener {
            val intent = Intent(this, MakingCourseSearchActivity::class.java)
            activityResultLauncher.launch(intent)
        }

        binding.btnAddListNext.setOnClickListener {
            val intent = Intent(this@MakingTourAddListActivity, MakingTourTimeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (isEditMode) {
            showExitEditDialog()
        } else {
            super.onBackPressed()
        }
    }

    private fun showExitEditDialog() {
        val dlgBinding = DialogMakingTourAddListSequenceBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
        sequenceDialog = dialogBuilder.setView(dlgBinding.root).show()

        val density = resources.displayMetrics.density
        val widthPx = (272 * density).toInt()
        val heightPx = (160 * density).toInt()

        sequenceDialog?.window?.setLayout(widthPx, heightPx)
        sequenceDialog?.window?.setGravity(Gravity.CENTER)
        sequenceDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dlgBinding.btnKeepGoing.setOnClickListener {
            sequenceDialog?.dismiss()
        }

        dlgBinding.btnExit.setOnClickListener {
            sequenceDialog?.dismiss()
        }
    }


    private fun initEditSequence() {
        isEditMode = !isEditMode
        addListAdapter?.setEditMode(isEditMode)

        if (isEditMode){
            binding.btnMakingTourAddListEditSequence.isGone = true
            binding.btnMakingTourAddListFinishSequence.isVisible = true
        }else{
            binding.btnMakingTourAddListEditSequence.isVisible = true
            binding.btnMakingTourAddListFinishSequence.isGone = true
        }
    }

    private fun setupItemTouchHelper() {
        val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {
            override fun isLongPressDragEnabled(): Boolean {
                return isEditMode  // edit mode 일때만 드래그
            }

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                if (toPosition == addListData.size - 1) {
                    return false
                }

                addListAdapter?.onItemMove(fromPosition, toPosition)
                return true

            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // 추후 swipe 기능 추가 되면 추가
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvMakingTourAddListPlace)
    }


    private fun updateRecyclerView(placeImage: String, placeName: String, placeAddress: String, location: LatLng) {
        val newItem = MakingTourAddListData(placeImage, placeName, placeAddress, location)
        AddListDataManager.addListData.add(newItem)
        addListAdapter?.notifyDataSetChanged()
        updateMarkers()
    }

    private fun initRecyclerView() {
        addListAdapter = MakingTourAddListAdapter(AddListDataManager.addListData, this)
        binding.rvMakingTourAddListPlace.adapter = addListAdapter
        binding.rvMakingTourAddListPlace.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun vectorToBitmap(drawableId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(this, drawableId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun addCustomMarker(location: LatLng, drawableId: Int): Marker? {
        val markerOptions = MarkerOptions()
            .position(location)
            .icon(vectorToBitmap(drawableId))
        return googleMap?.addMarker(markerOptions)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        updateMarkers()
    }

    private fun updateMarkers() {
        googleMap?.let { map ->
            map.clear() // 기존 마커 제거
            val boundsBuilder = LatLngBounds.builder()
            for (data in AddListDataManager.addListData) {
                val marker = addCustomMarker(data.placeLocation, R.drawable.ic_main_marker_unclicked)
                marker?.let {
                    boundsBuilder.include(it.position)
                }
            }
            if (AddListDataManager.addListData.isNotEmpty()) {
                val bounds = boundsBuilder.build()
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
            }
        } ?: run {
            Log.e("MakingTourAddListActivity", "GoogleMap is not initialized yet.")
        }
    }



    override fun onItemCountChanged(count: Int) {
        if (count == 8) {
            binding.clAddList.visibility = View.GONE
            binding.clAddListImpoosible.visibility = View.VISIBLE
        } else {
            binding.clAddListImpoosible.visibility = View.GONE
            binding.clAddList.visibility = View.VISIBLE
        }
    }
}