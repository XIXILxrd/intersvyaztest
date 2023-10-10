package com.example.intersvyaztest.domain

class GetSearchResultCoinInfoListUseCase(private val coinRepository: CoinRepository) {
    operator fun invoke(fromSymbol: String) = coinRepository.getSearchResultCoinInfoList(fromSymbol)
}