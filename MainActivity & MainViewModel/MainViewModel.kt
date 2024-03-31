package com.dicoding.submission_01_fundamental.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.submission_01_fundamental.data.local.Result
import com.dicoding.submission_01_fundamental.data.local.UserRepository
import com.dicoding.submission_01_fundamental.data.mode.Settings
import com.dicoding.submission_01_fundamental.data.response.ItemsItem


class MainViewModel(
    private val userRepository: UserRepository,
    private val mode: Settings) : ViewModel() {

    private val _listUsers = MutableLiveData<List<ItemsItem>>()
    val listUsers: LiveData<List<ItemsItem>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    init {
        searchUser("a")
    }

    fun searchUser(query: String) {
        userRepository.getUser(query).observeForever { res->
            when(res) {
                is Result.Load->{
                    _isLoading.value = true
                }
                is Result.Successful->{
                    _isLoading.value = false
                    _listUsers.value = res.data
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
    fun getMode(): LiveData<Boolean> {
        return mode.getThemeMode().asLiveData()
    }
}