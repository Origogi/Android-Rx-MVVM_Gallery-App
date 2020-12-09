package com.origogi.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.origogi.myapplication.model.ImageData
import com.origogi.myapplication.model.ImageDataProvider

class ImageDataViewModel(application: Application) : AndroidViewModel(application) {

    private val imageDataList : MutableLiveData<List<ImageData>> = MutableLiveData()

    init {
        ImageDataProvider.getImageDataList().subscribe {
            imageDataList.postValue(it);
        }
    }

    fun getAll() : LiveData<List<ImageData>> {
        return imageDataList
    }



}
