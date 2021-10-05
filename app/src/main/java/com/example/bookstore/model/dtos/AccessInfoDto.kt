package com.example.bookstore.model.dtos


import com.google.gson.annotations.SerializedName

data class AccessInfoDto(
    @SerializedName("accessViewStatus")
    val accessViewStatus: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("embeddable")
    val embeddable: Boolean?,
    @SerializedName("epub")
    val epub: EpubDto?,
    @SerializedName("pdf")
    val pdf: PdfDto?,
    @SerializedName("publicDomain")
    val publicDomain: Boolean?,
    @SerializedName("quoteSharingAllowed")
    val quoteSharingAllowed: Boolean?,
    @SerializedName("textToSpeechPermission")
    val textToSpeechPermission: String?,
    @SerializedName("viewability")
    val viewability: String?,
    @SerializedName("webReaderLink")
    val webReaderLink: String?
)