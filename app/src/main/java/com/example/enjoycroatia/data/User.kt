package com.example.enjoycroatia.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var email: String = "",
    var nickName: String = "",
    var favoriteDestinations: Map<String, String> = hashMapOf()
) : Parcelable