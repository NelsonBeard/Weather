package com.geekbrains.weather.model

import com.geekbrains.weather.Weather

interface Repository {
    fun getWeatherFromServer(): Weather?
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>

    fun weatherLoaded(weather: Weather?)
    fun addLoadListener(listener: OnLoadListener)
    fun removeLoadListener(listener: OnLoadListener)

    fun interface OnLoadListener {
        fun onLoaded()
    }
}