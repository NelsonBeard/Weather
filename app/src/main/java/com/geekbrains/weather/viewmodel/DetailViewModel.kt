package com.geekbrains.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.weather.model.Repository
import com.geekbrains.weather.model.RepositoryImpl
import java.lang.Exception
import kotlin.random.Random

class DetailViewModel : ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repository: Repository = RepositoryImpl()

    fun getData() : LiveData<AppState> = liveDataToObserve

    fun getWeather() {
        liveDataToObserve.value = AppState.Loading

        Thread{
            Thread.sleep(1000)
            if(Random.nextBoolean()){
                val weather = repository.getWeatherFromServer()
                liveDataToObserve.postValue(AppState.Success(weather))
            } else{
                liveDataToObserve.postValue(AppState.Error(Exception("Не удалось загрузить данные")))
            }
        }.start()

    }
}