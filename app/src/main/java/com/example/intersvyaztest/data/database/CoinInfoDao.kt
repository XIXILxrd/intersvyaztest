package com.example.intersvyaztest.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinInfoDao {
    @Query("SELECT * FROM full_price_list ORDER BY lastUpdate DESC")
    fun getPriceList(): LiveData<List<CoinInfoDatabaseModel>>

    @Query("SELECT * FROM full_price_list WHERE fromSymbol == :fSym LIMIT 1")
    fun getPriceInfoAboutCoin(fSym: String): LiveData<CoinInfoDatabaseModel>

    @Query("SELECT * FROM full_price_list WHERE isFavorite == 1")
    fun getFavoriteCoinsList(): LiveData<List<CoinInfoDatabaseModel>>

    @Query("SELECT * FROM full_price_list WHERE fromSymbol == :fSym")
    fun getSearchResultCoinInfoList(fSym: String): LiveData<List<CoinInfoDatabaseModel>>

    @Query("UPDATE full_price_list SET isFavorite = :isFavorite, description = :description WHERE fromSymbol = :fromSymbol")
    fun updateCoinInfo(fromSymbol: String, isFavorite: Boolean, description: String?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceList(priceList: List<CoinInfoDatabaseModel>)
}