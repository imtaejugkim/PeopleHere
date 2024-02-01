package com.example.people_here.AddPicture.PictureDB

import androidx.lifecycle.LiveData

class PictureRepo(private val pictureDao: PictureDao) {
    val realData: List<PictureEntity> = pictureDao.getPicture()

    suspend fun addPicture(picture:PictureEntity){
        pictureDao.addPicture(picture)
    }
}