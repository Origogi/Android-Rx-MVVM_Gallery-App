package com.origogi.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.origogi.myapplication.model.ImageData
import com.origogi.myapplication.model.ImageDataProvider

class ImageDataViewModel(application: Application) : AndroidViewModel(application) {

    private val imageDataList : MutableLiveData<List<ImageData>> = MutableLiveData()
    private val spanCount : MutableLiveData<Int> = MutableLiveData()

    init {
        ImageDataProvider.getImageDataList().subscribe {
            imageDataList.postValue(it);
        }
    }

    fun getAll() : LiveData<List<ImageData>> {
        return imageDataList
    }

    fun getSpanCount() : LiveData<Int> {
        return spanCount
    }

    fun updateSpanCount(count : Int) {
        spanCount.postValue(count)
    }



}
