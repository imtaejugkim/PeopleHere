package com.example.people_here.Remote

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("status") val status:Int,
    @SerializedName("code") val code:Int,
    @SerializedName("message") val message:String,
    @SerializedName("result") val result:T,//resut 는 안에 또 뭐가 들어와서 일단 T로 설정(재너릭?):굳이 명시 해주지 않아도, 그걸 받아서 쓰는

)
