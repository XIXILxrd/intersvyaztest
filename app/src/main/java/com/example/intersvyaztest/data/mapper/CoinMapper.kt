package com.example.intersvyaztest.data.mapper

import com.example.intersvyaztest.data.network.model.CoinInfoDTO
import com.example.intersvyaztest.data.network.model.CoinInfoJsonContainerDTO
import com.example.intersvyaztest.data.network.model.CoinNamesListDTO
import com.example.intersvyaztest.domain.CoinInfo
import com.example.intersvyaztest.data.database.CoinInfoDatabaseModel
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CoinMapper {
    fun mapDtoToDbModel(dto: CoinInfoDTO): CoinInfoDatabaseModel = CoinInfoDatabaseModel(
        fromSymbol = dto.fromSymbol,
        toSymbol = dto.toSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        lastMarket = dto.lastMarket,
        imageUrl = BASE_IMAGE_URL + dto.imageUrl,
        isFavorite = dto.isFavorite,
        description = null
    )

    fun mapJsonContainerToListContainer(jsonContainer: CoinInfoJsonContainerDTO): List<CoinInfoDTO> {
        val result = mutableListOf<CoinInfoDTO>()
        val jsonObject = jsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()

        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDTO::class.java
                )

                result.add(priceInfo)
            }
        }

        return result
    }

    fun mapNamesListToString(namesList: CoinNamesListDTO): String {
        return namesList.names?.map {
            it.coinNameDTO?.name
        }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDatabaseModel) = CoinInfo(
        fromSymbol = dbModel.fromSymbol,
        toSymbol = dbModel.toSymbol,
        price = dbModel.price,
        lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl,
        isFavorite = dbModel.isFavorite,
        description = dbModel.description
    )

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) {
            return ""
        }

        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())

        sdf.timeZone = TimeZone.getDefault()

        return sdf.format(date)
    }

    companion object {
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}