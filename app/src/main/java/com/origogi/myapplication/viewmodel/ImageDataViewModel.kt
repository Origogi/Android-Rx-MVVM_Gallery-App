package com.origogi.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.origogi.myapplication.STATE
import com.origogi.myapplication.model.ImageData
import com.origogi.myapplication.model.ImageDataProvider

class ImageDataViewModel(application: Application) : AndroidViewModel(application) {

    private val imageDataList: MutableLiveData<List<ImageData>> = MutableLiveData()
    private val spanCount: MutableLiveData<Int> = MutableLiveData()
    private val currentState: MutableLiveData<STATE> = MutableLiveData()

    init {
        fetchData()
        ImageDataProvider.getImageDataList().subscribe {

            if (it.isNotEmpty()) {
                imageDataList.postValue(it)
                currentState.postValue(STATE.LOADED)
            } else {
                currentState.postValue(STATE.ERROR)
            }
        }
    }

    fun getAll(): LiveData<List<ImageData>> {
        return imageDataList
    }

    fun getSpanCount(): LiveData<Int> {
        return spanCount
    }

    fun getCurrentState(): LiveData<STATE> {
        return currentState
    }

    fun updateSpanCount(count: Int) {
        spanCount.postValue(count)
    }

    fun fetchData() {
        currentState.postValue(STATE.LOADING)
        ImageDataProvider.fetch()
    }
}
