package com.example.intersvyaztest.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinNamesListDTO (
    @SerializedName("Data")
    @Expose
    val names: List<CoinNameContainerDTO>? = null
)