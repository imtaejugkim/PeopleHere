package com.example.people_here.Remote

import com.example.people_here.Data.ProfileData
import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("content") val content: String,
    @SerializedName("address") val address: String,
    @SerializedName("birth") val birth: String,
    @SerializedName("job") val job: String,
    @SerializedName("almaMater") val almaMater: String,
    @SerializedName("hobby") val hobby: String,
    @SerializedName("pet") val pet: String,
    @SerializedName("favourite") val favourite: String,
    @SerializedName("status") val status: String,
) {
    fun toProfileData() :ProfileData {
        return ProfileData(
            id = id,
            email = email,
            name = name,
            gender = gender,
            imageUrl = imageUrl,
            content = content,
            address = address,
            birth = birth,
            job = job,
            almaMater = almaMater,
            hobby = hobby,
            pet = pet,
            favourite = favourite,
            status = status,
        )
    }
}