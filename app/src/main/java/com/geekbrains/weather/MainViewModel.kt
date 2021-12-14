package com.geekbrains.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repository: Repository = RepositoryImpl()

    fun getData(): LiveData<AppState> = liveDataToObserve

    fun getWeatherFromLocalStorageRus() = getDataFromLocalSource(true)

    fun getWeatherFromLocalStorageWorld() = getDataFromLocalSource(false)

    fun getWeatherFromRemoteSource() = getDataFromLocalSource(true)

    private fun getDataFromLocalSource(isRussian: Boolean = true) {

        liveDataToObserve.value = AppState.Loading

        Thread {
            Thread.sleep(3000)

            val weather = if (isRussian) {
                repository.getWeatherFromLocalStorageRus()
            } else {
                repository.getWeatherFromLocalStorageWorld()
            }

            liveDataToObserve.postValue(AppState.Success(weather))
        }.start()
    }

}