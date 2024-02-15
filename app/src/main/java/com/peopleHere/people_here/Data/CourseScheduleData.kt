package com.peopleHere.people_here.Data

import com.google.gson.annotations.SerializedName

data class CourseScheduleData (
    @SerializedName("id") val id : Int,
    @SerializedName("date") val date : String,
    @SerializedName("time") val time : List<Int>,
    @SerializedName("status") val status : String,
    @SerializedName("participants") val participants : ArrayList<ScheduleParticipants>
)