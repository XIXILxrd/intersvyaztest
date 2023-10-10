package com.example.intersvyaztest.domain

class GetFavoriteCoinInfoListUseCase(private val coinRepository: CoinRepository) {
    operator fun invoke() = coinRepository.getFavoriteCoinInfoList()
}