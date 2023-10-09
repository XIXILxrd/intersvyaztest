package com.example.intersvyaztest.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.intersvyaztest.data.repository.CoinRepositoryImplementation
import com.example.intersvyaztest.domain.GetCoinInfoListUseCase
import com.example.intersvyaztest.domain.GetCoinInfoUseCase
import com.example.intersvyaztest.domain.LoadDataUseCase

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CoinRepositoryImplementation(application)

    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)


    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
        loadDataUseCase()
    }
}