package com.example.bookstore.model.dtos


import com.google.gson.annotations.SerializedName

data class ReadingModesDto(
    @SerializedName("image")
    val image: Boolean?,
    @SerializedName("text")
    val text: Boolean?
)