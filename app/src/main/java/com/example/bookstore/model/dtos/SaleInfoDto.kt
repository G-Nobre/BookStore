package com.example.bookstore.model.dtos


import com.google.gson.annotations.SerializedName

data class SaleInfoDto(
    @SerializedName("country")
    val country: String?,
    @SerializedName("isEbook")
    val isEbook: Boolean?,
    @SerializedName("saleability")
    val saleability: String?,
    @SerializedName("listPrice")
    val listPrice: ListPriceDto?,
    @SerializedName("retailPrice")
    val retailPrice: RetailPriceDto?,
    @SerializedName("buyLink")
    val buyLink: String?,
    @SerializedName("offers")
    val offers: List<OffersDto>?

)