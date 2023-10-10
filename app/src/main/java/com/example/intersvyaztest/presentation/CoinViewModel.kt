package com.example.intersvyaztest.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.intersvyaztest.data.repository.CoinRepositoryImplementation
import com.example.intersvyaztest.domain.GetCoinInfoListUseCase
import com.example.intersvyaztest.domain.GetCoinInfoUseCase
import com.example.intersvyaztest.domain.GetFavoriteCoinInfoListUseCase
import com.example.intersvyaztest.domain.GetSearchResultCoinInfoListUseCase
import com.example.intersvyaztest.domain.LoadDataUseCase

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CoinRepositoryImplementation(application)

    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)
    private val getFavoriteCoinInfoListUseCase = GetFavoriteCoinInfoListUseCase(repository)
    private val getSearchResultCoinInfoListUseCase = GetSearchResultCoinInfoListUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    fun getFavoriteCoins() = getFavoriteCoinInfoListUseCase.invoke()

    fun getSearchResult(fromSymbol: String) = getSearchResultCoinInfoListUseCase.invoke(fromSymbol)

    init {
        loadDataUseCase()
    }
}