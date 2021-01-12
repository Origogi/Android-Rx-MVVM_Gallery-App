package com.origogi.myapplication.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.origogi.myapplication.AppState
import com.origogi.myapplication.ViewType
import com.origogi.myapplication.model.ImageData
import com.origogi.myapplication.model.ImageDataProvider
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class ImageDataViewModel : ViewModel() {

    private val imageDataList: MutableLiveData<List<ImageData>> = MutableLiveData()
    private val viewType: MutableLiveData<ViewType> = MutableLiveData()
    private val appState: MutableLiveData<AppState> = MutableLiveData()
    private val disposable : Disposable? = null


    init {
        fetchData()
    }

    fun getImageDataList(): LiveData<List<ImageData>> {
        return imageDataList
    }

    fun getViewType(): LiveData<ViewType> {
        return viewType
    }

    fun getAppState(): LiveData<AppState> {
        return appState
    }

    fun updateViewType(type: ViewType) {
        viewType.postValue(type)
    }


    fun fetchData() {
        appState.postValue(AppState.LOADING)


        val disposable = ImageDataProvider.fetch()
            .doOnError {  }
            .subscribeOn(Schedulers.io())
            .subscribe({ list ->
                imageDataList.postValue(list)
                appState.postValue(AppState.LOADED)
            }, {
                appState.postValue(AppState.ERROR)
            }
            )

        disposable.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}
