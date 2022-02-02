package com.geekbrains.weather.view

import android.app.Application
import androidx.room.Room
import com.geekbrains.weather.model.HistoryDao
import com.geekbrains.weather.model.HistoryDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: App? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "History.db"

        fun getHistoryDao(): HistoryDao {

            if (db == null) {
                synchronized(HistoryDataBase::class.java) {
                    if (db == null) {
                        appInstance?.let { app ->
                            db = Room.databaseBuilder(
                                app.applicationContext,
                                HistoryDataBase::class.java,
                                DB_NAME
                            )
                                .allowMainThreadQueries()
                                .build()
                        } ?: throw Exception("Bad exception")
                    }
                }
            }
            return db!!.historyDao()
        }
    }
}