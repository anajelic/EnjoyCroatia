package com.example.enjoycroatia.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @BindingAdapter("imageFromURL")
    @JvmStatic
    fun setImageFromUrl(imageView: ImageView, path: String?) {
        path?.let { imageView.setImageWithURL(it) }
    }

}