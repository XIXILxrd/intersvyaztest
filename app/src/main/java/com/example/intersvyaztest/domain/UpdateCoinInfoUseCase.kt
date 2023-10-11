package com.example.intersvyaztest.domain

class UpdateCoinInfoUseCase(private val coinRepository: CoinRepository) {
    suspend operator fun invoke(fromSymbol: String, isFavorite: Boolean, description: String?) = coinRepository.updateCoinInfo(fromSymbol, isFavorite, description)
}