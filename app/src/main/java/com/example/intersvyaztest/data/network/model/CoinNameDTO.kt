package com.example.intersvyaztest.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinNameDTO (
    @SerializedName("Name")
    @Expose
    val name: String? = null
)