package com.dicoding.submission_01_fundamental.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FavUserDao {
    @Insert
    suspend fun addFav(user: UserEntity)

//    @Query("SELECT * FROM user")
//    fun getAllFav(): LiveData<List<UserEntity>>

    @Query("DELETE from user where username= :username")
    suspend fun deleteFav(username: String)

    @Query("SELECT * from user order by id asc")
    fun getAllFav(): LiveData<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * from user where username= :username)")
    fun isFav(username: String): LiveData<Boolean>
}