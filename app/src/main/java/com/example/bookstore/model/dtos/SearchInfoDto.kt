package com.example.bookstore.model.dtos


import com.google.gson.annotations.SerializedName

data class SearchInfoDto(
    @SerializedName("textSnippet")
    val textSnippet: String?
)