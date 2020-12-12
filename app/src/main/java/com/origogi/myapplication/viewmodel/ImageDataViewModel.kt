package com.origogi.myapplication.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.origogi.myapplication.STATE
import com.origogi.myapplication.model.ImageData
import com.origogi.myapplication.model.ImageDataProvider
import io.reactivex.schedulers.Schedulers

class ImageDataViewModel() : ViewModel() {

    private val imageDataList: MutableLiveData<List<ImageData>> = MutableLiveData()
    private val spanCount: MutableLiveData<Int> = MutableLiveData()
    private val currentState: MutableLiveData<STATE> = MutableLiveData()

    init {
        fetchData()
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
            .subscribeOn(Schedulers.io())
            .subscribe({ list ->
                if (list.isNotEmpty()) {
                    imageDataList.postValue(list)
                    currentState.postValue(STATE.LOADED)
                }
            }, {
                currentState.postValue(STATE.ERROR)
            }
        )
    }
}
