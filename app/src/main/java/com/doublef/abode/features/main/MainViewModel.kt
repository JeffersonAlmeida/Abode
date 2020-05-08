package com.doublef.abode.features.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doublef.abode.model.Photo
import com.doublef.abode.data.Repository
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _photos: MutableLiveData<List<Photo>> = MutableLiveData()
    fun photos(): LiveData<List<Photo>> = _photos

    init {
        getPhotos()
    }

    private fun getPhotos(){
        _photos.value = repository.getPhotos()
    }

    fun savePhoto(photo: String){
        val photosList = _photos.value as ArrayList<Photo>
        photosList.add(
            Photo(
                url = photo,
                date = Calendar.getInstance().time
            )
        )
        repository.savePhotos(Gson().toJson(photosList))
        _photos.value = photosList
    }
}
