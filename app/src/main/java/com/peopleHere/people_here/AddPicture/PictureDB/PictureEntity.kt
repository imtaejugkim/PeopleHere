package com.peopleHere.people_here.AddPicture.PictureDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "PictureEntitytable")

data class PictureEntity(
    val pictureUri: String,
    val loactionName: String,
    val UserId: String,//UserId로 비교해서 , 유저별 장소 등등을 구분하기 근데 만약 사진 다 지워지면 유저 아이디도 삭제해야하는데 그거 어케함??
    var itemType: Int,
    var order: Int
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0//요거 프라이머리 키로 자동 생성해준다는?
}
