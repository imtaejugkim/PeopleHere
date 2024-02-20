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
import com.peopleHere.people_here.Local.getJwt
import com.peopleHere.people_here.Main.MainSearchRecentAdapter
import com.peopleHere.people_here.Main.MakingCourseSearchAdapter
import com.peopleHere.people_here.MakingTour.MakingTourAddListActivity
import com.peopleHere.people_here.R
import com.peopleHere.people_here.Remote.AuthService
import com.peopleHere.people_here.Remote.RecentSearchOutputView
import com.peopleHere.people_here.Remote.RecentSearchResponse
import com.peopleHere.people_here.Remote.RecentSearchView
import com.peopleHere.people_here.databinding.ActivityMainSearchBinding

class MakingCourseSearchActivity : AppCompatActivity() ,
    MakingCourseSearchAdapter.OnPlaceClickListener ,
    MainSearchRecentAdapter.OnPlaceRecentClickListener,
    RecentSearchView,
    RecentSearchOutputView{
    lateinit var binding: ActivityMainSearchBinding
    private var makingSearchData: ArrayList<MakingCourseSearchData> = arrayListOf()
    private var makingRecentSearchData : ArrayList<RecentSearchResponse> = arrayListOf()
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
                if (s.isNullOrEmpty()) {
                    binding.clMainSearchRecent.visibility = View.VISIBLE
                    binding.clMainSearchOnSearching.visibility = View.GONE
                    initSearchOutputManager()
                } else {
                    binding.clMainSearchRecent.visibility = View.GONE
                    binding.clMainSearchOnSearching.visibility = View.VISIBLE
                    fetchPlaceSuggestions(s.toString())
                }
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
            .setCountry("KR")
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
        initSearchOutputManager()

        mainSearchRecentAdapter = MainSearchRecentAdapter(makingRecentSearchData, this)
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
            val placeName = data.searchRegion
            val placeAddress = data.searchPlace
            initSearchInputManager(data.placeId, placeName, placeAddress)

            val intent = Intent(this@MakingCourseSearchActivity, MakingCourseCheckActivity::class.java).apply {
                putExtra("placeId", data.placeId)
                putExtra("placeName", placeName)
                putExtra("placeAddress", placeAddress)
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

    private fun initSearchInputManager(placeId : String, placeName: String, placeAddress: String) {
        val token = getJwt()
        Log.d("token",token)
        if(token.isNotEmpty()){
            val authService = AuthService(this)
            authService.setRecentSearchView(this)
            authService.recentSearchInfo(placeId, placeName, placeAddress)
        }else{
            Log.d("token 오류","token 오류")
        }
    }

    private fun initSearchOutputManager() {
        val token = getJwt()
        Log.d("token",token)
        if(token.isNotEmpty()){
            val authService = AuthService(this)
            authService.setRecentSearchOutputView(this)
            authService.recentSearchOutputInfo()
        }else{
            Log.d("token 오류","token 오류")
        }
    }

    override fun RecentSearchLoading() {}

    override fun RecentSearchSuccess() {}

    override fun RecentSearchFailure(status: Int, message: String) {
        Log.d("오류",message)
    }
    override fun RecentSearchOutputViewLoading() {
        TODO("Not yet implemented")
    }

    override fun RecentSearchOutputViewSuccess(content : ArrayList<RecentSearchResponse>) {
        Log.d("place정보", content.toString())
        makingRecentSearchData.clear()
        makingRecentSearchData.addAll(content)

        mainSearchRecentAdapter?.notifyDataSetChanged()
    }

    override fun RecentSearchOutputViewFailure(status: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun onPlaceClick(data: RecentSearchResponse) {
        val placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(data.placeId, placeFields)

        placesClient.fetchPlace(request).addOnSuccessListener { response ->
            val place = response.place
            val placeName = data.placeName
            val placeAddress = data.placeAddress
            initSearchInputManager(data.placeId, placeName, placeAddress)

            val intent = Intent(this@MakingCourseSearchActivity, MakingCourseCheckActivity::class.java).apply {
                putExtra("placeId", data.placeId)
                putExtra("placeName", placeName)
                putExtra("placeAddress", placeAddress)
                putExtra("placeLatitude", place.latLng?.latitude)
                putExtra("placeLongitude", place.latLng?.longitude)
                Log.d("placeId",data.placeId)
                Log.d("placeName",data.placeName)
                Log.d("placeAddress",data.placeAddress)
                Log.d("placeLatitude",place.latLng?.latitude.toString())
                Log.d("placeLongitude",place.latLng?.longitude.toString())

            }
            startActivity(intent)
        }.addOnFailureListener { exception ->
            Log.d("오류",exception.toString())
        }
    }
}

