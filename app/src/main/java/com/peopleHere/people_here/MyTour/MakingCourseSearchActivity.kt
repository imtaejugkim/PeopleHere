package com.peopleHere.people_here.MyTour

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.ActivityMakingCourseSearchBinding

class MakingCourseSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakingCourseSearchBinding
    private lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakingCourseSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "YOUR_API_KEY")
        }

        placesClient = Places.createClient(this)

        val token = AutocompleteSessionToken.newInstance()
        val bounds = RectangularBounds.newInstance(
            LatLng(-33.880490, 151.184363), // Southwest corner
            LatLng(-33.858754, 151.229596) // Northeast corner
        )

        val autocompleteFragment = supportFragmentManager.findFragmentById(R.id.search_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Handle the selected place
                place.latLng?.let { latLng ->
                    binding.dummyText.text = latLng.toString()
                    Toast.makeText(this@MakingCourseSearchActivity, latLng.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(status: Status) {
                Log.e("PlacesError", status.statusMessage ?: "")
            }
        })

        // Configure the AutocompleteSupportFragment to use the bounds and session token
        autocompleteFragment.setCountries("AU", "NZ")
        autocompleteFragment.setLocationBias(bounds)
        // Note: setLocationRestriction(bounds) can be used instead of setLocationBias(bounds) depending on the desired behavior
        autocompleteFragment.setSessionToken(token)
    }}