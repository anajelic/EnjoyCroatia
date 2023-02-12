package com.example.enjoycroatia.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enjoycroatia.data.User
import com.example.enjoycroatia.util.FirebaseStorageManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor() : ViewModel() {

    val userLiveData: MutableLiveData<User?> = MutableLiveData()

    fun getUser() {
        viewModelScope.launch {
            userLiveData.postValue(FirebaseStorageManager.getUserAsync())
        }
    }

    fun logout(){
        FirebaseAuth.getInstance().signOut()
    }
}