package com.geekbrains.weather.model

import com.geekbrains.weather.Weather

interface LocalRepository {

    fun getAllHistory(): List<Weather>

    fun saveEntity(weather: Weather)

}