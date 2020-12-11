package com.origogi.myapplication.model


import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.jsoup.Jsoup

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

                val imageDataList = imageItems.map { imageData ->
                    val title = imageData.attr("data-title")
                    val imageUrl = imageData.select("img[class=jq-lazy]")[0].attr("data-src")
                    ImageData(title, imageUrl)
                }.filter { it.imageUrl.isNotEmpty() }
                    .toList()

                subject.onNext(imageDataList)

            } catch (e: Exception) {
                subject.onNext(emptyList())
            }
        }.start()
    }
}