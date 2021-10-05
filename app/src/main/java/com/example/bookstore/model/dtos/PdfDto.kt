package com.example.bookstore.model.dtos


import com.google.gson.annotations.SerializedName

data class PdfDto(
    @SerializedName("isAvailable")
    val isAvailable: Boolean?
)