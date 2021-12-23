package com.geekbrains.weather.model

data class WeatherDTO(
    val now: Long?,
    val fact: FactDTO?
)

data class FactDTO(
    val temp: Int?,
    val feels_like: Int?,
    val condition: String?,
    val humidity: Int?
)

