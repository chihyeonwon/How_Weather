package com.example.howweather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.howweather.data.WeatherData
import com.example.howweather.repository.WeatherRepository

import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "WeatherViewModel"

class WeatherViewModel(private val repository: WeatherRepository = WeatherRepository()) : ViewModel() {
    private val _regionText = MutableLiveData("서울특별시")
    val regionText: LiveData<String> = _regionText

    fun setRegion(city: String) {
        _regionText.value = city
    }

    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData get() = _weatherData

    private val _weatherList = MutableLiveData<List<WeatherData>>()
    val weatherList get() = _weatherList

    fun getWeather(date: String, city: String = regionText.value ?: "서울특별시") {
        viewModelScope.launch {
            runCatching {
                repository.getWeather(date, city)
            }.onSuccess { weatherResponse ->
                _weatherData.value = weatherResponse.toWeatherData()
            }.onFailure { e ->
                handleException(e)
            }
        }
    }

    fun getWeatherList(date: String, count: Int = 20) {
        viewModelScope.launch {
            runCatching {
                val city = regionText.value ?: "서울특별시"
                repository.getWeather(date, city, pageNo = count)
            }.onSuccess { weatherResponse ->
                _weatherList.value = weatherResponse.toWeatherList(count)
            }.onFailure { e ->
                handleException(e)
            }
        }
    }

    private fun handleException(e: Throwable) {
        when (e) {
            is HttpException -> {
                val errorJsonString = e.response()?.errorBody()?.string()
                Log.e(TAG, "HTTP error: $errorJsonString")
            }

            is IOException -> Log.e(TAG, "Network error: $e")
            else -> Log.e(TAG, "Unexpected error: $e")
        }
    }
}