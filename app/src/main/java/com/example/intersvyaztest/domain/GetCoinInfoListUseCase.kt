package com.example.intersvyaztest.domain

class GetCoinInfoListUseCase(private val coinRepository: CoinRepository) {
    operator fun invoke() = coinRepository.getCoinInfoList()
}