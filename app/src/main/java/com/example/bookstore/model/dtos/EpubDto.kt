package com.example.bookstore.model.dtos


import com.google.gson.annotations.SerializedName

data class EpubDto(
    @SerializedName("isAvailable")
    val isAvailable: Boolean?
)