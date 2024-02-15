package com.peopleHere.people_here.Data

import com.google.gson.annotations.SerializedName

data class ScheduleParticipants (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("imageUrl") val imageUrl : String
)