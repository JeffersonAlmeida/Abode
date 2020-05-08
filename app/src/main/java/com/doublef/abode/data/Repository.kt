package com.doublef.abode.data

import com.doublef.abode.model.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Repository (private var storage: Storage) {

    fun getPhotos(): List<Photo>{
        val type: Type = object : TypeToken<List<Photo?>?>() {}.type
        val photos = storage.getPhotos()
        return if (photos.isNotEmpty())
            Gson().fromJson<ArrayList<Photo>>(photos, type)
        else
            arrayListOf()
    }

    fun savePhotos(photos: String){
        storage.savePhotos(photos)
    }

}
