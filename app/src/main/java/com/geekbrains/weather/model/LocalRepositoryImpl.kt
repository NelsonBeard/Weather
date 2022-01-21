package com.geekbrains.weather.model

import com.geekbrains.weather.City
import com.geekbrains.weather.Weather
import java.util.*

class LocalRepositoryImpl(private val dao: HistoryDao) : LocalRepository {

    override fun getAllHistory(): List<Weather> {
        return dao.all()
            .map { entity ->
                Weather(
                    city = City(entity.city),
                    temperature = entity.temperature,
                    condition = entity.condition
                )
            }
    }

    override fun saveEntity(weather: Weather) {
        dao.insert(
            HistoryEntity(
                id = 0,
                city = weather.city.name,
                temperature = weather.temperature,
                condition = weather.condition,
                timestamp = Date().time
            )
        )
    }

}