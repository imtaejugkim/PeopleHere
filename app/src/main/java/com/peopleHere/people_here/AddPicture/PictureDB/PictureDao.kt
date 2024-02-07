package com.peopleHere.people_here.AddPicture.PictureDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PictureDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)

    fun addPicture(picture:PictureEntity)
    @Update
    fun UpdatePicture(picture:PictureEntity)

    @Query("DELETE FROM PictureEntitytable WHERE pictureUri = :pictureUri")//만약 같으면 지우기
    fun deletePicture(pictureUri: String)

    @Query("SELECT*FROM PictureEntitytable")//이걸 getProducts
    fun getPicture():List<PictureEntity>//LiveData 껴야 하는지?
}