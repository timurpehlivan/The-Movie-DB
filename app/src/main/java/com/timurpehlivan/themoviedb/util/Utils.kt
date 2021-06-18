package com.timurpehlivan.themoviedb.util

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.timurpehlivan.themoviedb.R

object Utils {
  fun formatImagePathToDisplay(initialString: String, stringToAppend: String?): String? {
    return if (!stringToAppend.isNullOrEmpty()) {
      initialString + stringToAppend
    } else null
  }

  fun displayImage(posterPath: String?, imageView: ImageView) {
    Picasso
      .with(imageView.context)
      .load(posterPath)
      .error(R.drawable.ic_placeholder)
      .placeholder(R.drawable.ic_placeholder)
      .into(imageView)
  }
}