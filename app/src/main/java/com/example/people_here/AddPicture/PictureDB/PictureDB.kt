package com.example.people_here.AddPicture.PictureDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PictureEntity::class], version = 6)

//singletone
abstract class PictureDB : RoomDatabase() {

    abstract fun getPictureDao(): PictureDao//나중에 Dao에서 선언한 기능들 쓰기 위해 선언

    companion object {
        //스태틱 이라고 생각(항상 있는 것)
        var instance: PictureDB? = null//instance를 사용하는 것
        fun getInstance(context: Context): PictureDB {//getInstance를 통해서 DB를 쓸 수 있는 것이군!!!
            if (instance == null) {//null인 경우!
                instance = Room.databaseBuilder(//db 만들어 주는 함수
                    context,//context가져옴 이렇게만 쓰려면 다른곳에서 requireContext해서 하나는 다르게
                    PictureDB::class.java,//class가져와야된다
                    "mystring-database"//db의 이름
                ).fallbackToDestructiveMigration()//버전 바꾸면 이전꺼 삭제
                    .build()
            }
            return instance!!//그냥 기존거 받아옴
        }
    }
}