package com.example.intersvyaztest.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.intersvyaztest.data.database.ApplicationDatabase
import com.example.intersvyaztest.data.mapper.CoinMapper
import com.example.intersvyaztest.data.worker.RefreshDataWorker
import com.example.intersvyaztest.domain.CoinInfo
import com.example.intersvyaztest.domain.CoinRepository

class CoinRepositoryImplementation(private val application: Application) : CoinRepository {
    private val coinInfoDao = ApplicationDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return coinInfoDao.getPriceList().map {
            it.map { dbModel ->
                mapper.mapDbModelToEntity(dbModel)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return coinInfoDao.getPriceInfoAboutCoin(fromSymbol).map {
            mapper.mapDbModelToEntity(it)
        }
    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(RefreshDataWorker.NAME, ExistingWorkPolicy.REPLACE, RefreshDataWorker.makeRequest())

    }
}