package com.nahlasamir244.taskhive.utils

import androidx.appcompat.widget.SearchView

//inline : for efficiency it copies the lambda instead of creating new object
//crossinline : kotlin doesn't allow calling function type in listeners cause
// it might return and cause
// unexpected behavior
inline fun SearchView.onQueryTextChanged(crossinline TextChangedListener: (String)->Unit){
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            TextChangedListener(newText.orEmpty())
            return true
        }

    })
}