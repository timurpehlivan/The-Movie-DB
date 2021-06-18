package com.timurpehlivan.themoviedb.util

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.timurpehlivan.themoviedb.R

inline fun SearchView.onQueryTextChanged(crossinline listener: (String) -> Unit) {
  this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
      return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
      listener(newText.orEmpty())
      return true
    }
  })
}

fun Fragment.displayErrorMessage(message: String?) {
  Toast.makeText(requireContext(), message ?: getString(R.string.generic_error_message), Toast.LENGTH_LONG).show()
}