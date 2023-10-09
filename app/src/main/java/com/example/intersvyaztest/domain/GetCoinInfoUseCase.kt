package com.example.intersvyaztest.domain

class GetCoinInfoUseCase(private val coinRepository: CoinRepository) {
    operator fun invoke(fromSymbol: String) = coinRepository.getCoinInfo(fromSymbol)
}