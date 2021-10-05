package com.example.bookstore.model.network

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


const val BASE_URL = "https://www.googleapis.com"
const val DEFAULT_PAGE_SIZE = 20
const val DEFAULT_IMAGE =
    "data:image/png;base64,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"


fun Completable.processResponse(onComplete:()->Unit) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(onComplete, { it.printStackTrace() })
}

fun <T> Single<T>.processResponse(handleResponse: (T) -> Unit) {
    this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(handleResponse, { it.printStackTrace() })
}