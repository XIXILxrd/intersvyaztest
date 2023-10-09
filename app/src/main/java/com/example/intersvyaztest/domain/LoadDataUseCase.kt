package com.example.intersvyaztest.domain

class LoadDataUseCase(private val coinRepository: CoinRepository) {
    operator fun invoke() = coinRepository.loadData()
}