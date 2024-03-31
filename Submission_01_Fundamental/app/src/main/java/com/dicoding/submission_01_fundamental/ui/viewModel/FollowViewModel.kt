package com.dicoding.submission_01_fundamental.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission_01_fundamental.data.local.Result
import com.dicoding.submission_01_fundamental.data.local.UserRepository
import com.dicoding.submission_01_fundamental.data.response.ItemsItem

class FollowViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _listFollower = MutableLiveData<List<ItemsItem>?>()
    val listFollower: LiveData<List<ItemsItem>?> = _listFollower

    private val _listFollowing = MutableLiveData<List<ItemsItem>?>()
    val listFollowing: LiveData<List<ItemsItem>?> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowers(username: String) {
        userRepository.getFollower(username).observeForever { res->
            when(res) {
                is Result.Load->{
                    _isLoading.value = true
                }
                is Result.Successful->{
                    _isLoading.value = false
                    _listFollower.value = res.data
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

    fun getFollowing(username: String) {
        userRepository.getFollowing(username).observeForever { res ->
            when(res) {
                is Result.Load->{
                    _isLoading.value = true
                }
                is Result.Successful->{
                    _isLoading.value = false
                    _listFollowing.value = res.data
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

}