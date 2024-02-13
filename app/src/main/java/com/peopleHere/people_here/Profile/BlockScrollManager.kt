package com.peopleHere.people_here.Profile

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class BlockScrollManager(context: Context) : LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
        return false
    }

    override fun canScrollHorizontally(): Boolean {
        return false
    }
}