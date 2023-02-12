package com.example.enjoycroatia.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enjoycroatia.data.Destination
import com.example.enjoycroatia.data.User
import com.example.enjoycroatia.util.FirebaseStorageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DestinationsViewModel @Inject constructor() : ViewModel() {

    val destinations: MutableLiveData<List<Destination>> =
        MutableLiveData(FirebaseStorageManager.getDestinations().value)


    val favoriteDestinations: MutableLiveData<List<Destination>> =
        MutableLiveData()

    val userLiveData: MutableLiveData<User> = MutableLiveData()

    fun refreshDestinations() {
        viewModelScope.launch {
            destinations.postValue(FirebaseStorageManager.getDestinationsAsync())
        }
    }

    fun getFavoriteDestinations() {
        viewModelScope.launch {
            favoriteDestinations.postValue(FirebaseStorageManager.getFavorites())
        }

    }

    fun getUser() {
        viewModelScope.launch {
            userLiveData.postValue(FirebaseStorageManager.getUserAsync())
        }
    }

    fun setVisitedDate(destination: Destination, date: String) {
        viewModelScope.launch {
            FirebaseStorageManager.setVisitedDate(destination, date)
            userLiveData.postValue(FirebaseStorageManager.getUserAsync())
        }
    }
}