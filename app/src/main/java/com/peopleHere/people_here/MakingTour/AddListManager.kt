package com.peopleHere.people_here.MakingTour

import com.peopleHere.people_here.Data.MakingTourAddListData

object AddListDataManager {
    val addListData: ArrayList<MakingTourAddListData> = arrayListOf()

    fun addNewItem(item: MakingTourAddListData) {
        addListData.add(item)
    }

    fun clearItems() {
        addListData.clear()
    }
}