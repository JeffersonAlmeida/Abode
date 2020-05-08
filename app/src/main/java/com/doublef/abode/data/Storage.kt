package com.doublef.abode.data

import android.content.SharedPreferences

class Storage(private var sharedPref: SharedPreferences) {

    fun getPhotos(): String {
        return sharedPref.getString(PHOTOS, "") ?: ""
    }

    fun savePhotos(photos: String){
        sharedPref.edit()?.putString(PHOTOS, photos)?.apply()
    }

    companion object {
        const val PHOTOS = "PHOTOS"
    }
}