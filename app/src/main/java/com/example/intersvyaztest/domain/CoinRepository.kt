package com.example.intersvyaztest.domain

import androidx.lifecycle.LiveData

interface CoinRepository {
    fun getCoinInfoList(): LiveData<List<CoinInfo>>

    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo>

    fun getFavoriteCoinInfoList(): LiveData<List<CoinInfo>>

    fun getSearchResultCoinInfoList(fromSymbol: String): LiveData<List<CoinInfo>>

    suspend fun updateCoinInfo(fromSymbol: String, isFavorite: Boolean, description: String?)

    fun loadData()
}