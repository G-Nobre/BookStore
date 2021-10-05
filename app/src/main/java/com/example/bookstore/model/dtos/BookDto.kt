package com.example.bookstore.model.dtos


import com.google.gson.annotations.SerializedName

data class BookDto(
    @SerializedName("accessInfo")
    val accessInfo: AccessInfoDto?,
    @SerializedName("etag")
    val etag: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("saleInfo")
    val saleInfo: SaleInfoDto?,
    @SerializedName("searchInfo")
    val searchInfo: SearchInfoDto?,
    @SerializedName("selfLink")
    val selfLink: String?,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfoDto?
)