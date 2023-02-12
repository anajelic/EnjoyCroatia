package com.example.enjoycroatia.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.enjoycroatia.R

fun ImageView.setImageWithURL(path: String?) {
    Glide.with(this)
        .load(path)
        .apply(
            RequestOptions().placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
        )
        .into(this)
}

