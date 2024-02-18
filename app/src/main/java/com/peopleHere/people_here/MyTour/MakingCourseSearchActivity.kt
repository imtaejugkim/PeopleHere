package com.peopleHere.people_here.MyTour

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.peopleHere.people_here.Data.MainSearchData
import com.peopleHere.people_here.Data.MakingCourseSearchData
import com.peopleHere.people_here.Main.MainSearchRecentAdapter
import com.peopleHere.people_here.Main.MakingCourseSearchAdapter
import com.peopleHere.people_here.MakingTour.MakingTourAddListActivity
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivityMainSearchBinding

class MakingCourseSearchActivity : AppCompatActivity() , MakingCourseSearchAdapter.OnPlaceClickListener{
    lateinit var binding: ActivityMainSearchBinding
    private var makingSearchData: ArrayList<MakingCourseSearchData> = arrayListOf()
    private var mainSearchRecentAdapter: MainSearchRecentAdapter? = null
    private var makingSearchAdapter : MakingCourseSearchAdapter ?= null
    private lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainSearchBinding.inflate(layoutInflater)

        // api 키 입력
        Places.initialize(applicationContext, "AIzaSyB3WLa8PNBaMOK2OQm1U64Hb6RetEu-E1g")
        // places client 생성
        placesClient = Places.createClient(this)

        initRecyclerView()

        binding.ivMainSearchArrow.setOnClickListener {
            initBackStack()
        }

        // 자동완성 텍스트 와쳐
        binding.etMainSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.clMainSearchRecent.visibility = View.VISIBLE
                binding.clMainSearchOnSearching.visibility = View.GONE

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clMainSearchRecent.visibility = View.GONE
                binding.clMainSearchOnSearching.visibility = View.VISIBLE
                fetchPlaceSuggestions(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        setContentView(binding.root)
    }

    private fun fetchPlaceSuggestions(query: String) {
        val token = AutocompleteSessionToken.newInstance()
        val request = FindAutocompletePredictionsRequest.builder()
            .setTypeFilter(TypeFilter.ESTABLISHMENT)
            .setSessionToken(token)
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            val suggestions = response.autocompletePredictions.map { prediction ->
                MakingCourseSearchData(
                    prediction.getPrimaryText(null).toString(),
                    prediction.getSecondaryText(null).toString(),
                    prediction.placeId
                )
            }

            makingSearchAdapter?.updateData(ArrayList(suggestions))
        }.addOnFailureListener { exception ->
            Log.e("MainSearchActivity", "Place not found: ${exception.message}")
            // place 못찾을 때 오류 처리
        }
    }

    private fun initBackStack() {
        finish()
    }


    private fun initRecyclerView() {
        mainSearchRecentAdapter = MainSearchRecentAdapter(makingSearchData)
        binding.rvMainSearchRecent.adapter = mainSearchRecentAdapter
        binding.rvMainSearchRecent.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )

        makingSearchAdapter = MakingCourseSearchAdapter(makingSearchData, this)
        binding.rvMainSearchOnSearching.adapter = makingSearchAdapter
        binding.rvMainSearchOnSearching.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
    }

    override fun onPlaceClick(data: MakingCourseSearchData) {
        val placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(data.placeId, placeFields)

        placesClient.fetchPlace(request).addOnSuccessListener { response ->
            val place = response.place
            val intent = Intent(this@MakingCourseSearchActivity, MakingCourseCheckActivity::class.java).apply {
                putExtra("placeId", data.placeId)
                putExtra("placeName", data.searchRegion)
                putExtra("placeAddress", data.searchPlace)
                putExtra("placeLatitude", place.latLng?.latitude)
                putExtra("placeLongitude", place.latLng?.longitude)
                Log.d("placeId",data.placeId)
                Log.d("placeName",data.searchRegion)
                Log.d("placeAddress",data.searchPlace)
                Log.d("placeLatitude",place.latLng?.latitude.toString())
                Log.d("placeLongitude",place.latLng?.longitude.toString())
            }
            startActivity(intent)
        }.addOnFailureListener { exception ->
            Log.d("오류",exception.toString())
        }
    }
}

