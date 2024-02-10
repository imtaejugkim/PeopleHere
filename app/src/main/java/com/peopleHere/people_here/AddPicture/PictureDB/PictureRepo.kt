package com.peopleHere.people_here.AddPicture.PictureDB

class PictureRepo(private val pictureDao: PictureDao) {
    val realData: List<PictureEntity> = pictureDao.getPicture()

    suspend fun addPicture(picture:PictureEntity){
        pictureDao.addPicture(picture)
    }
}