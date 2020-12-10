package com.origogi.myapplication.model

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.jsoup.Jsoup
import java.io.IOException

object ImageDataProvider {

    private val subject = PublishSubject.create<List<ImageData>>()

    fun getImageDataList() : Observable<List<ImageData>> {
        return subject
    }

    fun fetch() {
        Thread {
            try {
                val doc = Jsoup.connect("https://www.gettyimagesgallery.com/collection/sasha/").get()

                val imageItems = doc.select("div[class=grid-item image-item col-md-4]")
                val imageDataList = mutableListOf<ImageData>()

                imageItems.forEach { imageData ->
                    val title = imageData.attr("data-title")
                    val imageUrl = imageData.select("img[class=jq-lazy]")[0].attr("data-src").toString()
                    imageDataList.add(ImageData(title, imageUrl))
                }

                subject.onNext(imageDataList)

            } catch (e: IOException) {
                subject.onNext(emptyList())
            }
        }.start()
    }
}