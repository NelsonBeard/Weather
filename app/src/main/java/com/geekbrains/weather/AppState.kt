package com.geekbrains.weather

sealed class AppState{
    data class Success(val weather: Weather): AppState()

    data class Error(val error: Throwable): AppState()

    object Loading: AppState()
}
