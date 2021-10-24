package com.example.spacex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex.LaunchListQuery
import com.example.spacex.repo.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(private val repository: LaunchRepository) : ViewModel() {
    private val _data: MutableLiveData<List<LaunchListQuery.Launch>> = MutableLiveData()
    val data: LiveData<List<LaunchListQuery.Launch>>
        get() = _data

    init {
        viewModelScope.launch {
            _data.postValue(repository.getLaunchList(IO))
        }
    }
}