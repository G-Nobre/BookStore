package com.example.bookstore.model.dtos


import com.google.gson.annotations.SerializedName

data class IndustryIdentifierDto(
    @SerializedName("identifier")
    val identifier: String?,
    @SerializedName("type")
    val type: String?
)