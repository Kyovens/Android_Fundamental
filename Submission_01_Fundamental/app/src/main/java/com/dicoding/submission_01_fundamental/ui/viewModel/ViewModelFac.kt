package com.dicoding.submission_01_fundamental.ui.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.submission_01_fundamental.data.local.UserRepository
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submission_01_fundamental.data.mode.Inject
import com.dicoding.submission_01_fundamental.data.mode.Settings
import com.dicoding.submission_01_fundamental.data.mode.dataStore
import java.lang.IllegalArgumentException

class ViewModelFac private constructor(private val pref: Settings, private val userRepository: UserRepository): ViewModelProvider.NewInstanceFactory(){

    companion object{
        @Volatile
        private var instance: ViewModelFac? = null
        fun getInstance(application: Application): ViewModelFac = instance ?: synchronized(this) {
            instance?: ViewModelFac(Settings.getInstance(application.dataStore), Inject.provideRepo(application.applicationContext))
        }.also { instance = it }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when(modelClass){
        MainViewModel::class.java -> MainViewModel(userRepository, pref)
        FollowViewModel::class.java -> FollowViewModel(userRepository)
        DetailViewModel::class.java -> DetailViewModel(userRepository)
        FavViewModel::class.java -> FavViewModel(userRepository)
        SettingViewModel::class.java-> SettingViewModel(pref)
        else -> throw IllegalArgumentException("Unkwon modelclass")
    } as T
}