package com.peopleHere.people_here.MakingTour

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.peopleHere.people_here.Data.MakingTourAddListData
import com.peopleHere.people_here.Data.PlaceData
import com.peopleHere.people_here.Data.PostData

object AddListDataManager {
    val addListData: ArrayList<MakingTourAddListData> = arrayListOf()
    private val markersMap: MutableMap<LatLng, Marker> = mutableMapOf()


    fun addNewItem(item: MakingTourAddListData) {
        addListData.add(item)

    }
    fun clearItems() {
        addListData.clear()
    }

    fun addMarker(location: LatLng, marker: Marker) {
        markersMap[location] = marker
    }

    fun removeMarker(location: LatLng) {
        markersMap[location]?.remove()
        markersMap.remove(location)
    }

    fun clearMarkers() {
        markersMap.values.forEach { it.remove() }
        markersMap.clear()
    }
}