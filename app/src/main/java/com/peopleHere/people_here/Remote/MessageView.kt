package com.peopleHere.people_here.Remote

import com.peopleHere.people_here.Data.ChatData
import com.peopleHere.people_here.Data.ProfileData

interface MessageView {

    fun MessageSuccess(content : ArrayList<ChatData>)
    fun MessageFailure(status : Int, message : String)
}
