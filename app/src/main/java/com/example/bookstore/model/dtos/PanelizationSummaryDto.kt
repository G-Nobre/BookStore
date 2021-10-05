package com.example.bookstore.model.dtos


import com.google.gson.annotations.SerializedName

data class PanelizationSummaryDto(
    @SerializedName("containsEpubBubbles")
    val containsEpubBubbles: Boolean?,
    @SerializedName("containsImageBubbles")
    val containsImageBubbles: Boolean?
)