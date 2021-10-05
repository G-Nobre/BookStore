package com.example.bookstore.model.dtos


import com.google.gson.annotations.SerializedName

data class ImageLinksDto(
    @SerializedName("smallThumbnail")
    val smallThumbnail: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?
)