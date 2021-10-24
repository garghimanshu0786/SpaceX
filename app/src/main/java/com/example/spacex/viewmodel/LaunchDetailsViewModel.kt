package com.example.spacex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex.LaunchDetailsQuery
import com.example.spacex.repo.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchDetailsViewModel @Inject constructor(private val repository: LaunchRepository) : ViewModel() {

    private val _data: MutableLiveData<LaunchDetailsQuery.Launch> = MutableLiveData()
    val data: LiveData<LaunchDetailsQuery.Launch>
        get() = _data

    fun getDetails(id: String) {
        viewModelScope.launch {
            _data.postValue(repository.getLaunchDetails(Dispatchers.IO, id))
        }
    }
}