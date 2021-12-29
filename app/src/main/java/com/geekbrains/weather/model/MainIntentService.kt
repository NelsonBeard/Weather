package com.geekbrains.weather.model

import android.app.IntentService
import android.content.Intent
import com.geekbrains.weather.Weather

class MainIntentService : IntentService("MainIntentService") {

    companion object {
        const val TAG = "MainIntentService"
    }


    override fun onHandleIntent(intent: Intent?) {
        Thread.sleep(1000)

        intent?.getParcelableExtra<Weather>("WEATHER_EXTRA")?.let { weather ->
            WeatherLoader.load(weather.city, object : WeatherLoader.OnWeatherLoadListener {
                override fun onLoaded(weatherDTO: WeatherDTO) {
                    applicationContext.sendBroadcast(
                        Intent(
                            applicationContext,
                            MainReceiver::class.java
                        ).apply {
                            action = MainReceiver.WEATHER_LOAD_SUCCESS
                            putExtra(
                                "WEATHER_EXTRA", Weather(
                                    condition = weatherDTO.fact?.condition ?: "",
                                    temperature = weatherDTO.fact?.temp ?: 0,
                                    feelsLike = weatherDTO.fact?.feels_like ?: 0,
                                    humidity = weatherDTO.fact?.humidity ?: 0,
                                    icon = weatherDTO.fact?.icon ?: ""
                                    )
                            )
                        })
                }

                override fun onFailed(throwable: Throwable) {
                    applicationContext.sendBroadcast(
                        Intent(
                            applicationContext,
                            MainReceiver::class.java
                        ).apply {
                            action = MainReceiver.WEATHER_LOAD_FAILED
                        })
                }
            })

        }
    }
}
