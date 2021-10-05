package com.example.bookstore.model.dtos


import com.google.gson.annotations.SerializedName

data class ResponseDto(
    @SerializedName("items")
    val items: List<BookDto>?,
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("totalItems")
    val totalItems: Int?
)