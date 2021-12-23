package com.geekbrains.weather.model

import com.geekbrains.weather.Weather
import com.geekbrains.weather.getRussianCities
import com.geekbrains.weather.getWorldCities

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather = Weather()
    override fun getWeatherFromLocalStorageRus(): List<Weather> = getRussianCities()
    override fun getWeatherFromLocalStorageWorld(): List<Weather> = getWorldCities()
}