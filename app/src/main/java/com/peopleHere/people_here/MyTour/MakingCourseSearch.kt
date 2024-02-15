package com.peopleHere.people_here.MyTour

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.peopleHere.people_here.R
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.Places

class MakingCourseSearch : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_making_course_search)
        val dummyText = findViewById<TextView>(R.id.dummyText)


        if (!Places.isInitialized()){
            Places.initialize(applicationContext,"AIzaSyB3WLa8PNBaMOK2OQm1U64Hb6RetEu-E1g")
        }

        // Initialize the AutocompleteSupportFragment.
        // Specify the types of place data to return.
        val autocompleteSupportFragment = (supportFragmentManager.findFragmentById(R.id.cl_main_search)
                as AutocompleteSupportFragment).setPlaceFields(
                    listOf(Place.Field.LAT_LNG,Place.Field.NAME)
                )


        // Set up a PlaceSelectionListener to handle the response.
        autocompleteSupportFragment.setOnPlaceSelectedListener(object :PlaceSelectionListener{
            override fun onPlaceSelected(p0: Place) {
              if (p0.latLng!=null){
                  val latLng = p0.latLng
                  dummyText.text = latLng.toString()
                  Toast.makeText(this@MakingCourseSearch, latLng.toString(), Toast.LENGTH_SHORT).show()
              }
            }

            override fun onError(p0: Status) {
                    Log.e("error",p0.statusMessage.toString())
            }

        })


    }
}