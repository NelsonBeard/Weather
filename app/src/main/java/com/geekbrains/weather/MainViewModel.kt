package com.geekbrains.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Exception
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getData() : LiveData<AppState> = liveDataToObserve

    fun getWeather() {
        liveDataToObserve.value = AppState.Loading

        Thread{
            Thread.sleep(3000)
            if(Random.nextBoolean()){
                liveDataToObserve.postValue(AppState.Success(Weather("Ufa", -10, "30%")))
            } else{
                liveDataToObserve.postValue(AppState.Error(Exception("Не удалось загрузить данные")))
            }
        }.start()

    }
}