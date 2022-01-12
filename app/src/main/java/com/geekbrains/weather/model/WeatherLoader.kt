package com.geekbrains.weather.model

import android.os.Build
import android.util.Log
import com.geekbrains.weather.City
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object WeatherLoader {

    private val WeatherAPI = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(WeatherAPI::class.java)

    private const val MY_API_KEY = "7408799b-53f9-4971-bb0b-217461a8df94"

    fun loadRetrofit(city: City, listener: OnWeatherLoadListener) {
        WeatherAPI.getWeather(MY_API_KEY, city.lat, city.lon)
            .enqueue(object : Callback<WeatherDTO> {

                override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                    if (response.isSuccessful) {
                        response.body()?.let { listener.onLoaded(it) }
                    } else {
                        listener.onFailed(Exception(response.message()))
                    }
                }

                override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                    listener.onFailed(t)
                }
            })
    }

    interface OnWeatherLoadListener {
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(throwable: Throwable)
    }
}

