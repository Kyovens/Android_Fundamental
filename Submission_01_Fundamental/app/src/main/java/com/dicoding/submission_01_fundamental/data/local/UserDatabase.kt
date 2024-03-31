package com.dicoding.submission_01_fundamental.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false)
abstract class UserDatabase : RoomDatabase(){
    abstract fun favUserDao() : FavUserDao

    companion object{
        @Volatile
        private var INSTANCE : UserDatabase? = null

        @JvmStatic
        fun getInstance(context: Context):
                UserDatabase{
            if (INSTANCE == null) {
                synchronized(UserDatabase::class.java){
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext, UserDatabase::class.java,
                            "userDatabase"
                        )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE as UserDatabase
        }
    }
}