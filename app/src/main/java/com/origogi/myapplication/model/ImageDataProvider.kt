package com.origogi.myapplication.model

import io.reactivex.Single
import org.jsoup.Jsoup


//  <div class="grid-item image-item col-md-4" data-orientation="horizontal" data-title="Streamline Revue" data-date="20181126">
//  <a href="https://www.gettyimagesgallery.com/images/streamline-revue/?collection=sasha">
//  <div class="item-wrapper">
//  <img class="jq-lazy" data-src="https://www.gettyimagesgallery.com/wp-content/uploads/2018/12/GettyImages-3349362-1024x790.jpg" alt="Streamline Revue from Sasha fine art photography" />
//  <div class="text-wrapper mt-auto">
//  <h5 class="image-title">Streamline Revue</h5>
//  <button class="btn btn-primary btn-enquire" data-id="3448" >Enquire</button>
//  </div>
//  </div>
//  </a>
//  </div>
//

object ImageDataProvider {

    fun fetch() : Single<List<ImageData>> {

        return Single.create<List<ImageData>> { callback ->

            try {
                val doc = Jsoup.connect("https://www.gettyimagesgallery.com/collection/sasha/").get()

                val imageDataList =
                    doc.select("div[class=grid-item image-item col-md-4]")
                        .map { imageData ->
                            val title = imageData.attr("data-title")
                            val imageUrl = imageData.select("img[class=jq-lazy]").attr("data-src")
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