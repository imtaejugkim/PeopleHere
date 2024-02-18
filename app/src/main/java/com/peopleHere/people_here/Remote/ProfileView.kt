package com.peopleHere.people_here.Remote

import com.peopleHere.people_here.Data.MainData
import com.peopleHere.people_here.Data.ProfileData

interface ProfileView {
    fun ProfileSuccess(content : ProfileData)
    fun MainFailure(status : Int, message : String)
}