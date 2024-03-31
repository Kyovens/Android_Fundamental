package com.dicoding.submission_01_fundamental.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submission_01_fundamental.data.local.UserRepository
import kotlinx.coroutines.launch

class FavViewModel(private val userRepository: UserRepository): ViewModel() {

    fun getAllFav() = userRepository.getAllFav()
    fun deleteFav(username: String) {
        viewModelScope.launch {
            userRepository.deletFav(username)
        }
    }
}