package com.example.howweather.model


import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("response")
    val response: Response
)