package com.dicoding.submission_01_fundamental.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.submission_01_fundamental.data.response.DetailUserResponse
import com.dicoding.submission_01_fundamental.data.response.ItemsItem
import com.dicoding.submission_01_fundamental.data.retrofit.ApiService

class UserRepository(private val apiService: ApiService, private val favUserDao: FavUserDao) {

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favUserDao: FavUserDao
        ): UserRepository = instance ?: synchronized(this){
            instance ?: UserRepository(apiService, favUserDao)
        }.also { instance = it }
    }

    fun getUser(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Load)
        try {
            val res = apiService.getAllUser(username)
            val item = res.items
            emit(Result.Successful(item))
        } catch (e: Exception) {
            Log.d("UserRepo", "getAllUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowing(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Load)
        try {
            val res = apiService.getFollowing(username)
            if (res.isEmpty()) emit(Result.Empty)
            else emit(Result.Successful(res))
        } catch (e: Exception) {
            Log.d("UserRepo", "getFollowing: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollower(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Load)
        try {
            val res = apiService.getFollowers(username)
            if (res.isEmpty()) emit(Result.Empty)
            else emit(Result.Successful(res))
        } catch (e: Exception) {
            Log.d("UserRepo", "getFollower: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun detailUser(username: String): LiveData<Result<DetailUserResponse>> = liveData {
        emit(Result.Load)
        try {
            val res = apiService.getDetailUser(username)
            emit(Result.Successful(res))
        } catch (e: Exception) {
            Log.d("UserRepo", "detailUser: ${e.message.toString()}")
        }
    }

    fun getAllFav(): LiveData<List<UserEntity>> {
        return favUserDao.getAllFav()
    }
    fun isFav(username: String): LiveData<Boolean>{
        return favUserDao.isFav(username)
    }
    suspend fun addFav(userEntity: UserEntity) {
        favUserDao.addFav(userEntity)
    }
    suspend fun deletFav(username: String) {
        favUserDao.deleteFav(username)
    }

}