package com.dicoding.submission_01_fundamental.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.submission_01_fundamental.data.mode.Settings
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: Settings): ViewModel() {
    fun getMode(): LiveData<Boolean> {
        return pref.getThemeMode().asLiveData()
    }
    fun saveMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            pref.saveTheme(isDarkMode)
        }
    }
}