package com.example.intersvyaztest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CoinInfoDatabaseModel::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {
    companion object {

        private var db: ApplicationDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): ApplicationDatabase {
            synchronized(LOCK) {
                db?.let {
                    return it
                }

                val instance =
                    Room.databaseBuilder(
                        context,
                        ApplicationDatabase::class.java,
                        DB_NAME
                    )
                        .build()

                db = instance

                return instance
            }
        }
    }

    abstract fun coinPriceInfoDao(): CoinInfoDao
}