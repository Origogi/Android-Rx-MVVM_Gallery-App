package com.origogi.myapplication.model

import io.reactivex.Single
import org.jsoup.Jsoup

object ImageDataProvider {

    fun fetch() : Single<List<ImageData>> {

        return Single.create<List<ImageData>> { callback ->

            try {
                val doc = Jsoup.connect("https://www.gettyimagesgallery.com/collection/sasha/").get()

                val imageDataList =
                    doc.select("div[class=grid-item image-item col-md-4]")
                        .map { imageData ->
                            val title = imageData.attr("data-title")
                            val imageUrl = imageData.select("img[class=jq-lazy]")[0].attr("data-src")
                            ImageData(title, imageUrl)
                        }
                        .filter { it.imageUrl.isNotEmpty() }
                        .toList()

                callback.onSuccess(imageDataList)

            } catch (e: Exception) {
                callback.onError(e)
            }
        }
    }
}