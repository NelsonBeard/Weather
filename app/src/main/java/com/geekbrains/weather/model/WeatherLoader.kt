package com.geekbrains.weather.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.geekbrains.weather.City
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object WeatherLoader {

    private const val MY_API_KEY = "7408799b-53f9-4971-bb0b-217461a8df94"

    fun load(city: City, listener: OnWeatherLoadListener) {

        val handler = Handler(Looper.myLooper() ?: Looper.getMainLooper())

        Thread {
            var urlConnection: HttpsURLConnection? = null

            try {
                val uri =
                    URL("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")

                urlConnection = uri.openConnection() as HttpsURLConnection
                with(urlConnection) {
                    addRequestProperty(
                        "X-Yandex-API-Key", MY_API_KEY
                    )
                    requestMethod = "GET"
                    readTimeout = 10000
                    connectTimeout = 10000

                }

                val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    reader.lines().collect(Collectors.joining("\n"))
                } else {
                    ""
                }

                Log.d("DEBUG", "result: $result")

                val weatherDTO = Gson().fromJson(result, WeatherDTO::class.java)
                handler.post { listener.onLoaded(weatherDTO) }

            } catch (e: Exception) {
                handler.post {
                    listener.onFailed(e)
                }
                Log.e("", "Fail connection", e)
            } finally {
                urlConnection?.disconnect()
            }
        }.start()
    }


    interface OnWeatherLoadListener {
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(throwable: Throwable)
    }
}