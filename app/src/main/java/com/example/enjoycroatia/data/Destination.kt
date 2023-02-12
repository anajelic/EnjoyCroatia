package com.example.enjoycroatia.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Destination(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var imageURL: String = ""
) : Parcelable