package com.dicoding.submission_01_fundamental.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submission_01_fundamental.data.local.Result
import com.dicoding.submission_01_fundamental.data.local.UserEntity
import com.dicoding.submission_01_fundamental.data.local.UserRepository
import com.dicoding.submission_01_fundamental.data.response.DetailUserResponse
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel(){


    private val _userEntity = MutableLiveData<UserEntity>()
    val userEntity: LiveData<UserEntity> = _userEntity

    private val _detailUser = MutableLiveData<DetailUserResponse?>()
    val detailUser: LiveData<DetailUserResponse?> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun addFav(userEntity: UserEntity){
        viewModelScope.launch {
            userRepository.addFav(userEntity)
        }
    }

    fun deleteFav(username: String) {
        viewModelScope.launch {
            userRepository.deletFav(username)
        }
    }

    fun getDetailUser(username: String) {
        userRepository.detailUser(username).observeForever { result ->
            when(result) {
                is Result.Load -> {
                    _isLoading.value = true
                }
                is Result.Successful->{
                    _isLoading.value = false
                    _detailUser.value = result.data
                    _userEntity.value = UserEntity(
                        0,
                        result.data.login,
                        result.data.avatarUrl)
                }
                is Result.Error->{
                    _isLoading.value = false
                }
                is Result.Empty->{
                    _isLoading.value = false
                }
            }
        }
    }
    fun isFav(username: String) = userRepository.isFav(username)
}