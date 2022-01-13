package com.geekbrains.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.weather.Weather
import com.geekbrains.weather.model.LocalRepository
import com.geekbrains.weather.model.LocalRepositoryImpl
import com.geekbrains.weather.model.Repository
import com.geekbrains.weather.model.RepositoryImpl
import com.geekbrains.weather.view.App
import java.lang.Exception
import kotlin.random.Random

class DetailViewModel : ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repository: Repository = RepositoryImpl
    private val localRepo: LocalRepository = LocalRepositoryImpl(App.getHistoryDao())

    fun getData() : LiveData<AppState> = liveDataToObserve

    fun saveHistory(weather: Weather){
        localRepo.saveEntity(weather)
    }

    fun getWeather() {
        liveDataToObserve.value = AppState.Loading

        Thread{
            Thread.sleep(500)
            if(Random.nextBoolean()){
                val weather = repository.getWeatherFromServer()
                liveDataToObserve.postValue(AppState.Success(weather))
            } else{
                liveDataToObserve.postValue(AppState.Error(Exception("Не удалось загрузить данные")))
            }
        }.start()

    }
}