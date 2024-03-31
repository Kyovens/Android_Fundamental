package com.dicoding.submission_01_fundamental.data.mode

import android.content.Context
import com.dicoding.submission_01_fundamental.data.local.UserDatabase
import com.dicoding.submission_01_fundamental.data.local.UserRepository
import com.dicoding.submission_01_fundamental.data.retrofit.ApiConfig

object Inject {
    fun provideRepo(context: Context):
            UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val favDao = database.favUserDao()
        return UserRepository.getInstance(apiService, favDao)
    }
}