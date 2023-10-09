package com.example.intersvyaztest.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinNameContainerDTO (
    @SerializedName("CoinInfo")
    @Expose
    val coinNameDTO: CoinNameDTO? = null
)