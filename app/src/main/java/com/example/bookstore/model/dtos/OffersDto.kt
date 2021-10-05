package com.example.bookstore.model.dtos

import com.google.gson.annotations.SerializedName

data class OffersDto(
    @SerializedName("finskyOfferType")
    val finskyOfferType: String?,
    @SerializedName("listPrice")
    val listPrice: ListPriceDto?,
    @SerializedName("retailPrice")
    val retailPrice: RetailPriceDto?
)
